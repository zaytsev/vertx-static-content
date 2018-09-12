package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class Web extends AbstractVerticle {

  @Override
  public void start(final Future<Void> startFuture) throws Exception {
    final Router router = Router.router(vertx);
    router.get("/page").handler(ctx -> {
      System.out.println("=== page");
      ctx.response().sendFile("webroot/test.html", sendResult -> {
        if (sendResult.succeeded()) {
          System.out.println("=== send OK");
        } else {
          System.out.println("=== send failure");
          sendResult.cause().printStackTrace();
        }
      });
    });
    router.get("/static/*").handler(StaticHandler.create("webroot/static/"));

    vertx.createHttpServer().requestHandler(router::accept).listen(9999, "localhost", listenResult -> {
      if (listenResult.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(listenResult.cause());
      }
    });
  }

}
