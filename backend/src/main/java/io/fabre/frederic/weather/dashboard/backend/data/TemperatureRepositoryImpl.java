package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    public class TemperatureRepositoryImpl implements TemperatureRepository {

        private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureRepositoryImpl.class);

        MongoClient client;

        public TemperatureRepositoryImpl(final MongoClient client) {
            this.client = client;
        }

        @Override
        public TemperatureRepository save(Temperature temperature, Handler<AsyncResult<Temperature>> resultHandler) {
            JsonObject json = JsonObject.mapFrom(temperature);
            client.save(Temperature.DB_TABLE, json, res -> {
                if (res.succeeded()) {
                    LOGGER.info("Temperature created: {}", res.result());
                    temperature.setId(res.result());
                    resultHandler.handle(Future.succeededFuture(temperature));
                } else {
                    LOGGER.error("Temperature not created", res.cause());
                    resultHandler.handle(Future.failedFuture(res.cause()));
                }
            });
            return this;
        }
    }