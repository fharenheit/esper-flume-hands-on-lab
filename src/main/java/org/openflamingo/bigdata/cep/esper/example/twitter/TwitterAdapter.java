package org.openflamingo.bigdata.cep.esper.example.twitter;

import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Twitter4J를 이용하여 Twitter 메시지를 수집하는 Twitter Adapter.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class TwitterAdapter {

    /**
     * 기본 생성자.
     */
    public TwitterAdapter() {
    }

    public void start() throws Exception {
        // Twitter에 접근하기 위한 기본 계정 정보를 로딩한다.
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        Properties twitterProperties = new Properties();
        File twitter4jPropsFile = new File("src/main/resources/twitter4j.properties");
        if (!twitter4jPropsFile.exists()) {
            System.err.println("twitter4j.properties 파일을 로딩할 수 없습니다.");
            return;
        }
        twitterProperties.load(new FileInputStream(twitter4jPropsFile));

        // 환경설정 파일에서 로딩한 사용자 정보를 설정한다.
        configurationBuilder.setDebugEnabled(Boolean.valueOf(twitterProperties.getProperty("debug")));
        configurationBuilder.setUser(twitterProperties.getProperty("user"));
        configurationBuilder.setPassword(twitterProperties.getProperty("password"));

        // Twitter Stream을 생성한다.
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();

        // CEP Engine을 시작한다.
        EsperCEPEngine esperCEPEngine = new EsperCEPEngine();
        esperCEPEngine.start();

        // Twitter 메시지를 수신하는 리스너를 생성한다.
        StatusAdapter listener = new TwitterStatusListener(esperCEPEngine);
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}
