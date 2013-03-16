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
     * 기본 생성자.
     */
    public EsperUpdateListener() {
    }

    @Override
    public void update(EventBean[] newEvent, EventBean[] oldEvent) {
        for (EventBean eventBean : newEvent) {
            System.out.println("Country Count :: " + eventBean.get("count(distinct country)") + "\t\t" + eventBean.get("country"));
        }
    }

}
