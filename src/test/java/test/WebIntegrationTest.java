package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class WebIntegrationTest {

  @BeforeAll
  public static void init(final Vertx vertx, final VertxTestContext testContext) {
    vertx.deployVerticle(Web.class, new DeploymentOptions(), testContext.succeeding(v -> testContext.completeNow()));
  }

  @Test
  public void testPage(final Vertx vertx, final VertxTestContext testContext) {
    vertx.createHttpClient().get(9999, "localhost", "/page", response -> {
      testContext.verify(() -> {
        assertEquals(200, response.statusCode());
        testContext.completeNow();
      });
    }).setTimeout(1_000).exceptionHandler(testContext::failNow).end();
  }

  @Test
  public void testJs(final Vertx vertx, final VertxTestContext testContext) {
    vertx.createHttpClient().get(9999, "localhost", "/static/js/main.js", response -> {
      testContext.verify(() -> {
        assertEquals(200, response.statusCode());
        testContext.completeNow();
      });
    }).setTimeout(1_000).exceptionHandler(testContext::failNow).end();
  }
}
