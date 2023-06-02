package eu.happycoders.shop.bootstrap;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

/**
 * Launcher for the application: starts the Undertow server and deploys the shop application.
 *
 * @author Sven Woltmann
 */
public class Launcher {

  private UndertowJaxrsServer server;

  public static void main(String[] args) {
    new Launcher().startOnDefaultPort();
  }

  public void startOnDefaultPort() {
    server = new UndertowJaxrsServer();
    startServer();
  }

  public void startOnPort(int port) {
    server = new UndertowJaxrsServer().setPort(port);
    startServer();
  }

  private void startServer() {
    server.start();
    server.deploy(RestEasyUndertowShopApplication.class);
  }

  public void stop() {
    server.stop();
  }
}
