package io.vertx.slides.examples.c_failure;

import io.vertx.core.Vertx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class A_HttpServer {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    vertx.createHttpServer()
        .requestHandler(request -> {
          //fault();
          request.response()
              .putHeader("Access-Control-Allow-Origin", "*")
              .end("World ! ("
                  + formatter.format(LocalDateTime.now()) +
                  ")");
        })
        .listen(8081, result -> {
          if (result.failed()) {
            System.out.println("D'oh ! Can't start the" +
                " HTTP server");
          } else {
            System.out.println("HTTP server started on" +
                " port " + result.result().actualPort());
          }
        });
  }

  private static void fault() {
    throw new RuntimeException("Fault !");
  }
}
