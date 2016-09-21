package io.vertx.slides.examples.b_eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class C_RR_Consumer {

  public static void main(String[] args) {
    Vertx.clusteredVertx(new VertxOptions(),
        maybeVertx -> {
          if (maybeVertx.failed()) {
            System.out.println("Cannot create clustered vert.x");
          } else {
            Vertx vertx = maybeVertx.result();
            AtomicInteger counter = new AtomicInteger();

            vertx.eventBus().consumer("eventbus-example-rr", message -> {
             // fail();
              message.reply("Hello " + message.body() + " (" + counter.getAndIncrement() + ")");
            });
          }
        }

    );
  }

  private static void fail() {
    throw new RuntimeException("D'oh !");
  }
}
