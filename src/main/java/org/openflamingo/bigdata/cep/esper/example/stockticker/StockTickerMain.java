package org.openflamingo.bigdata.cep.esper.example.stockticker;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openflamingo.bigdata.cep.esper.example.stockticker.eventbean.PriceLimit;
import org.openflamingo.bigdata.cep.esper.example.stockticker.eventbean.StockTick;
import org.openflamingo.bigdata.cep.esper.example.stockticker.monitor.StockTickerMonitor;
import org.openflamingo.bigdata.cep.esper.example.stockticker.monitor.StockTickerResultListener;

import java.util.LinkedList;

public class StockTickerMain implements Runnable {

    private static final Log log = LogFactory.getLog(StockTickerMain.class);

    private final String engineURI;

    private final boolean continuousSimulation;

    public static void main(String[] args) {
        new StockTickerMain("StockTicker", true).run();
    }

    public StockTickerMain(String engineURI, boolean continuousSimulation) {
        this.engineURI = engineURI;
        this.continuousSimulation = continuousSimulation;
    }

    public void run() {

        // 이벤트 탐지할 대상을 등록한다.
        Configuration configuration = new Configuration();
        configuration.addEventType("PriceLimit", PriceLimit.class.getName());
        configuration.addEventType("StockTick", StockTick.class.getName());

        // CEP 엔진을 초기화한다.
        log.info("Setting up EPL");
        EPServiceProvider epService = EPServiceProviderManager.getProvider(engineURI, configuration);
        epService.initialize();

        // 이벤트를 모니터링하고 처리하는 이벤트 리스너를 등록한다.
        new StockTickerMonitor(epService, new StockTickerResultListener());

        // 테스트 이벤트를 발생시켜 CEP 엔진으로 전달한다.
        log.info("Generating test events: 1 million ticks, ratio 2 hits, 100 stocks");
        StockTickerEventGenerator generator = new StockTickerEventGenerator();
        LinkedList stream = generator.makeEventStream(1000000, 500000, 100, 25, 30, 48, 52, false);
        log.info("Generating " + stream.size() + " events");

        log.info("Sending " + stream.size() + " limit and tick events");
        for (Object theEvent : stream) {
            epService.getEPRuntime().sendEvent(theEvent);
            if (continuousSimulation) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    log.debug("Interrupted", e);
                    break;
                }
            }
        }
        log.info("Done.");
    }
}
