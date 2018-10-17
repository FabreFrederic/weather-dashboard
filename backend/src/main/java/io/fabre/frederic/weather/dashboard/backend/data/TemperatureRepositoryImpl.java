package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.reactivex.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TemperatureRepositoryImpl implements TemperatureRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureRepositoryImpl.class);

    MongoClient mongoClient;

    public TemperatureRepositoryImpl(final MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public TemperatureRepository save(Temperature temperature, Handler<AsyncResult<Temperature>> resultHandler) {
        JsonObject json = JsonObject.mapFrom(temperature);
        mongoClient.save(Temperature.DB_TABLE, json, res -> {
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

    @Override
    public TemperatureRepository findTodayTemperature(Handler<AsyncResult<List<Temperature>>> resultHandler) {
        String date = "2018-12-02T00:00:00+00:00";
        JsonObject query = new JsonObject().put("date", new JsonObject().put("$lte", date));

        FindOptions options = new FindOptions().setBatchSize(10);

        final List<Temperature> temperatures = new ArrayList<>();

        mongoClient.rxFindWithOptions(Temperature.DB_TABLE, query, options)
                .map(JsonArray::new)
                .subscribe(rows -> {
                            for (Object json : rows.getList()) {
                                LOGGER.debug(((JsonObject) json).encodePrettily());
                                temperatures.add(new Temperature((JsonObject) json));
                            }
                            resultHandler.handle(Future.succeededFuture(temperatures));
                        },
                        err -> {
                            LOGGER.error("Error while retrieving today's temperature", err);
                            resultHandler.handle(Future.failedFuture(err.getMessage()));
                        });
        return this;
    }

    public void findTodayTemperature2(Handler<AsyncResult<List<Temperature>>> resultHandler) {
        JsonObject query = new JsonObject()
                .put("author", "J. R. R. Tolkien");
        FindOptions options = new FindOptions().setBatchSize(10);
        mongoClient.findBatchWithOptions("book", query, options)
                .exceptionHandler(throwable -> throwable.printStackTrace())
                .endHandler(v -> System.out.println("End of research"))
                .handler(doc -> System.out.println("Found doc: " + doc.encodePrettily()));

    }
}


