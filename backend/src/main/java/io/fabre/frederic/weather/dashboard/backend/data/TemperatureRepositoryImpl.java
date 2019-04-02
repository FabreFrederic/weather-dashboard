package io.fabre.frederic.weather.dashboard.backend.data;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TemperatureRepositoryImpl implements TemperatureRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureRepositoryImpl.class);
    private MongoClient mongoClient;
    private static final String mongoDbDateFieldName = "date";

    public TemperatureRepositoryImpl(final MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public TemperatureRepository save(Reading reading, Handler<AsyncResult<Reading>> resultHandler) {
        JsonObject json = JsonObject.mapFrom(reading);

        mongoClient.save(Reading.COLLECTION, json, res -> {
            if (res.succeeded()) {
                LOGGER.info("Reading created: {}", res.result());
                reading.setId(res.result());
                resultHandler.handle(Future.succeededFuture(reading));
            } else {
                LOGGER.error("Reading not created", res.cause());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }

    @Override
    public TemperatureRepository findTodayReadings(final SensorEnvironment sensorEnvironment,
                                                   final SensorType sensorType,
                                                   final Handler<AsyncResult<List<Reading>>> resultHandler) {
        final String date = getAtMidNightTodayDate();
        JsonObject query = new JsonObject().put(mongoDbDateFieldName, new JsonObject().put("$gte", date));
        query.put("sensorEnvironment", new JsonObject().put("$eq", sensorEnvironment));
        query.put("sensorType", new JsonObject().put("$eq", sensorType));

        final List<Reading> readings = new ArrayList<>();

        mongoClient.rxFind(Reading.COLLECTION, query)
                .map(JsonArray::new)
                .subscribe(rows -> {
                            for (Object json : rows.getList()) {
                                LOGGER.debug(((JsonObject) json).encodePrettily());
                                readings.add(new Reading((JsonObject) json));
                            }
                            resultHandler.handle(Future.succeededFuture(readings));
                        },
                        err -> {
                            LOGGER.error("Error while retrieving today's air temperature", err);
                            resultHandler.handle(Future.failedFuture(err.getMessage()));
                        });

        return this;
    }

    private String getAtMidNightTodayDate() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return formatter.format(todayMidnight);
    }
}


