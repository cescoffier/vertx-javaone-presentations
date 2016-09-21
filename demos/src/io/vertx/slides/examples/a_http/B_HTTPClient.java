package io.vertx.slides.examples.a_http;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.ext.web.Router;

/**
 * Port: 8082
 * Cors enabled
 * <p>
 * "Hello " + buffer
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class B_HTTPClient {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    HttpClient client = vertx.createHttpClient(
        new HttpClientOptions()
            .setDefaultHost(SERVICE_HOST)
            .setDefaultPort(SERVICE_PORT));

    vertx.createHttpServer()
        .requestHandler(request ->
            call(client).setHandler(
                ar -> request.response()
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .end("Hello " + ar.result()))
        )
        .listen(PORT, result -> {
          if (result.failed()) {
            System.out.println("D'oh ! Can't " +
                "start the HTTP server");
          } else {
            System.out.println("HTTP server " +
                "started on port "
                + result.result().actualPort());
          }
        });
  }

  private static Future<String> call(HttpClient client) {
    Future<String> future = Future.future();
    client.getNow("/",
        response -> response.bodyHandler(buffer -> future.complete(buffer.toString())));
    return future;
  }


  public static final int PORT = 8082;
  public static final int SERVICE_PORT = 8081;
  public static final String SERVICE_HOST = "localhost";
}
