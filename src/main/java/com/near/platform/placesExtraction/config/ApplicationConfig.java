package com.near.platform.placesExtraction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Prathap on 19 Jul, 2019
 */
@Component
@ConfigurationProperties
public class ApplicationConfig {

    private Livy livy;
    private Consul consul;
    private Mongo mongo;

    public static class Mongo {
        private String uriInConsul;
        private String database;

        public String getUriInConsul() {
            return uriInConsul;
        }

        public void setUriInConsul(String uriInConsul) {
            this.uriInConsul = uriInConsul;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }
    }

    public static class Consul {
        private String host;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

    public static class Livy {
        private String host;
        private String createBatchEndpoint;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getCreateBatchEndpoint() {
            return createBatchEndpoint;
        }

        public void setCreateBatchEndpoint(String createBatchEndpoint) {
            this.createBatchEndpoint = createBatchEndpoint;
        }
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public Consul getConsul() {
        return consul;
    }

    public void setConsul(Consul consul) {
        this.consul = consul;
    }

    public Livy getLivy() {
        return livy;
    }

    public void setLivy(Livy livy) {
        this.livy = livy;
    }

}