package io.fabre.frederic.weather.dashboard.backend.proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.serviceproxy.ServiceBinder;

public class SockJSVerticle extends AbstractVerticle {

    private static final String PROXY_ADDRESS = "proxy.address";

    public void start() {
        // Create the client object
//        MyService service = new MyServiceImpl(vertx);

        // Register the handler
        ServiceBinder serviceBinder = new ServiceBinder(vertx);
//        serviceBinder.setAddress(PROXY_ADDRESS).register(MyService.class, service);

        Router router = Router.router(vertx);
        BridgeOptions options = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress(PROXY_ADDRESS))
                .addOutboundPermitted(new PermittedOptions().setAddress(PROXY_ADDRESS));

        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));

        // Serve the static resources
        router.route().handler(StaticHandler.create());

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
