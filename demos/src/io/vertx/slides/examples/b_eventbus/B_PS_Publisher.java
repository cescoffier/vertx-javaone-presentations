package io.vertx.slides.examples.b_eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class B_PS_Publisher {

  public static void main(String[] args) {
    Vertx.clusteredVertx(new VertxOptions(),
        maybeVertx -> {
          if (maybeVertx.failed()) {
            System.out.println("Cannot create clustered" +
                " vert.x");
          } else {
            Vertx vertx = maybeVertx.result();
            AtomicInteger counter = new AtomicInteger();
            vertx.setPeriodic(2000, l -> {
              vertx.eventBus().publish("eventbus-example",
                  new JsonObject()
                      .put("from", "java")
                      .put("message",
                          "hello from java " +
                          "(" + counter.getAndIncrement() + ")"));
            });
          }
        });
  }

}
