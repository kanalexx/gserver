package gserver;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

/**
 * @author Alexander Kanunnikov
 */

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("log4j.properties");
        Server server = new Server(8088);

        Handler frontend = new Frontend();
        server.setHandler(frontend);

        server.start();
        server.join();
    }
}