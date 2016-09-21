package io.vertx.slides.examples.c_failure;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class C_HttpClient_CB {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);

    CircuitBreaker cb = CircuitBreaker.create("circuit-breaker", vertx, new CircuitBreakerOptions()
        .setTimeout(1000)
        .setMaxFailures(3)
        .setResetTimeout(5000)
        .setFallbackOnFailure(true));

    HttpClient client = vertx.createHttpClient(
        new HttpClientOptions()
            .setDefaultHost("localhost")
            .setDefaultPort(8081));

    router.route().handler(CorsHandler.create("*"));
    router.get("/").handler(rc -> {
      cb.executeWithFallback(future -> {
        System.out.println("Calling...");
        client.get("/", response -> {
          response.bodyHandler(buffer -> {
            System.out.println("Replying...");
            future.complete("Hello " + buffer.toString());
          });
        })
            .exceptionHandler(future::fail)
            .end();
      }, t -> "Sorry... "
          + t.getMessage() + " (" + cb.state() + ")")
          .setHandler(content ->
              rc.response().end(content.result()));
    });


    vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(8082, result -> {
          if (result.failed()) {
            System.out.println("D'oh ! Can't start the " +
                "HTTP server");
          } else {
            System.out.println("HTTP server started on " +
                "port " + result.result().actualPort());
          }
        });
  }
}
