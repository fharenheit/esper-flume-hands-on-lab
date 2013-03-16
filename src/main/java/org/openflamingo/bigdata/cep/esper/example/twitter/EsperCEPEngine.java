package org.openflamingo.bigdata.cep.esper.example.twitter;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import twitter4j.Place;

/**
 * Esper CEP Engine.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class EsperCEPEngine {

    EPServiceProvider epService;

    EPStatement statement;

    public void start() {
        // Twitter의 사용자 정보를 이벤트로 추가한다.
        Configuration configuration = new Configuration();
        configuration.addEventType("Place", Place.class.getName());

        epService = EPServiceProviderManager.getDefaultProvider();
        String expression = "select country, count(country) from twitter4j.Place.win:time(10 sec)";
        statement = epService.getEPAdministrator().createEPL(expression);

        EsperUpdateListener listener = new EsperUpdateListener();
        statement.addListener(listener);
    }

    public EPServiceProvider getEpService() {
        return epService;
    }
}
