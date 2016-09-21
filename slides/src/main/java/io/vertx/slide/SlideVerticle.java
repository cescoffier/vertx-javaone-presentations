package io.vertx.slide;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * The verticle serving the slides.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class SlideVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    Router router = Router.router(vertx);

    setupEventBusBridge(router);
    router.get("/*").handler(StaticHandler.create("assets"));

    vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(9000, ar -> {
          if (ar.succeeded()) {
            System.out.println("Slides served on http://localhost:9000");
            future.complete();
          } else {
            System.err.println("Cannot start the HTTP server: " + ar.cause().getMessage());
            future.fail(ar.cause());
          }
        });
  }

  private void setupEventBusBridge(Router router) {
    String[] addresses = {
        // Initial hello demo
        "data.hello",
        // Event bus bridge demo
        "data.messages",
        // Polyglot demo
        "data.random",

        // Eventbus examples
        "eventbus-example",
        "eventbus-example-rr",

        // Load balancing
        "load-balancing"
    };

    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    BridgeOptions options = new BridgeOptions().setReplyTimeout(3000L);
    for (String address : addresses) {
      options
          .addInboundPermitted(new PermittedOptions().setAddress(address))
          .addOutboundPermitted(new PermittedOptions().setAddress(address));
    }
    sockJSHandler.bridge(options);

    router.route("/eventbus/*").handler(sockJSHandler);
  }
}
