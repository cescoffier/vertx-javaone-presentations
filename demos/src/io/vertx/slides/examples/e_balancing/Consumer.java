package io.vertx.slides.examples.e_balancing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Consumer {

  public static void main(String[] args) {
    Vertx.clusteredVertx(new VertxOptions(),
        v -> {
          Vertx vertx = v.result();
          cleanShutdownHook(vertx);
          String name =
              ManagementFactory.getRuntimeMXBean().getName();
          System.out.println("Name: " + name);
          vertx.eventBus()
              .consumer("load-balancing", message -> {
            message.reply("message handled by " + name);
          });
        });
  }

  private static void cleanShutdownHook(Vertx vertx) {
    Runtime.getRuntime().addShutdownHook(new Thread(
        () -> {
          CountDownLatch latch = new CountDownLatch(1);
          vertx.close(v -> {
            latch.countDown();
          });
          try {
            latch.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
    ));
  }
}
