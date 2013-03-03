package org.openflamingo.bigdata.cep.esper.example.twitter;

/**
 * Twitter 메시지를 수신하는 Adapter를 실행하는 Starter.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class TwitterAdapterStarter {

    public static void main(String[] args) throws Exception {
        TwitterAdapter twitterAdapter = new TwitterAdapter();
        twitterAdapter.start();
    }

}
