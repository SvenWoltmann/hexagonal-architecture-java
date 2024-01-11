package eu.happycoders.shop.bootstrap;

import eu.happycoders.shop.SpringAppConfig;
import org.springframework.boot.SpringApplication;

/**
 * Launcher for the application: starts the Spring application.
 *
 * @author Sven Woltmann
 */
public class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(SpringAppConfig.class, args);
  }
}
