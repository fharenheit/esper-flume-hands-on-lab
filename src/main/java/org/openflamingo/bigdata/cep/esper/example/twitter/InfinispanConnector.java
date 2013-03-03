package org.openflamingo.bigdata.cep.esper.example.twitter;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

/**
 * Infinispan Connector.
 *
 * @author Edward KIM
 * @since 0.1
 */
public class InfinispanConnector {

    /**
     * Remote Cache
     */
    private RemoteCache<Object, Object> cache;

    public void connect() {
        RemoteCacheManager cacheContainer = new RemoteCacheManager();
        cache = cacheContainer.getCache("locationCache");
    }

    public void set(String key, Long count) {
        if(cache == null) {
            System.out.println("null");
        }
        cache.put(key, String.valueOf(count));
    }
}
