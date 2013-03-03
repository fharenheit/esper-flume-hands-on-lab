package org.openflamingo.bigdata.cep.esper.example.twitter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Esper CEP Update Listener.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class EsperUpdateListener implements UpdateListener {

    /**
     * Infinispan Connector.
     */
    InfinispanConnector connector = new InfinispanConnector();

    /**
     * 기본 생성자.
     */
    public EsperUpdateListener() {
        connector.connect();
    }

    @Override
    public void update(EventBean[] newEvent, EventBean[] oldEvent) {
        for (EventBean eventBean : newEvent) {
            System.out.println(eventBean.get("count(location)"));
            connector.set("location", (Long) eventBean.get("count(location)"));
        }
    }

}
