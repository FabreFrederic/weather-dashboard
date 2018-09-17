package io.fabre.frederic.weather.dashboard.backend;

import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepository;
import io.fabre.frederic.weather.dashboard.backend.data.TemperatureRepositoryImpl;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoVerticle extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoVerticle.class);
	private static final String MONGO_SERVICE = "mongo.service";

	@Override
	public void start() throws Exception {
		LOGGER.info("Mongo verticle is starting");
		ConfigStoreOptions file = new ConfigStoreOptions().setType("file").
				setConfig(new JsonObject().put("path", "application.json"));
		ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(file));
		retriever.getConfig(conf -> {
			JsonObject datasourceConfig = conf.result().getJsonObject("datasource");
			JsonObject config = new JsonObject();
			String host = System.getenv("MONGODB_HOST");
			LOGGER.info("host : {}", host);
			if (StringUtils.isBlank(host)) {
				host =  datasourceConfig.getString("host");
			}

			config.put("host", host);
			config.put("port", datasourceConfig.getInteger("port"));
			config.put("db_name", datasourceConfig.getString("db_name"));

			final MongoClient client = MongoClient.createShared(vertx, config);
			final ServiceBinder binder = new ServiceBinder(vertx);
			// Create an instance of your service implementation
			final TemperatureRepository service = new TemperatureRepositoryImpl(client);
			// Register the handler
			binder.setAddress(MONGO_SERVICE).register(TemperatureRepository.class, service);
		});
	}
	
}
