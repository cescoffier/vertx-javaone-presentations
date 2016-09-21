package io.vertx.slides.examples.a_http;

import io.vertx.core.Vertx;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Start a HTTP Server.
 * Port 8081
 * Header Access-Control-Allow-Origin, *
 */
public class A_HTTPServer {

  private final static SimpleDateFormat formatter =
      new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.createHttpServer()
        .requestHandler(request -> {
          String date = formatter.format(new Date());
          request.response()
              .putHeader("Access-Control-Allow-Origin", "*")
              .end("javaone ! <br> " + date + "<br>" + Thread.currentThread().getName() );
        })
        .listen(8081, ar -> {
          if (ar.failed()) {
            System.out.println("D'oh ! " + ar.cause().getMessage());
          } else {
            System.out.println("Server started on port " + ar.result().actualPort());
          }
        });
  }

}
