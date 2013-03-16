package org.openflamingo.bigdata.cep.esper.example.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.User;

/**
 * Twitter Status를 처리하는 이벤트 리스너.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class TwitterStatusListener extends StatusAdapter {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(TwitterStatusListener.class);

    /**
     * Esper CEP Engine
     */
    private EsperCEPEngine esperCEPEngine;

    /**
     * 기본 생성자.
     *
     * @param esperCEPEngine Esper CEP Engine
     */
    public TwitterStatusListener(EsperCEPEngine esperCEPEngine) {
        this.esperCEPEngine = esperCEPEngine;
    }

    @Override
    public void onStatus(Status status) {
        // 위치 정보가 있다면 CEP 엔진으로 전달한다.
        String country = status.getPlace().getCountry();
        if (country != null && !country.trim().equals("")) {
            esperCEPEngine.getEpService().getEPRuntime().sendEvent(status.getPlace());
        }
    }
}
