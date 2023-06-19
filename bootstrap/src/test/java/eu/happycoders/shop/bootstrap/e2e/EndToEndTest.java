package eu.happycoders.shop.bootstrap.e2e;

import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.TEST_PORT;

import eu.happycoders.shop.bootstrap.Launcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

abstract class EndToEndTest {

  private static Launcher launcher;

  @BeforeAll
  static void init() {
    launcher = new Launcher();
    launcher.startOnPort(TEST_PORT);
  }

  @AfterAll
  static void stop() {
    launcher.stop();
  }
}
