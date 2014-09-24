package basicServer;

import org.apache.log4j.Logger;

import eventHandler.StreamSayHelloEventHandler;
import eventHandler.StreamUpdateProfileEventHandler;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName()); 

	public static final int PORT = 5000;

	public static void main(String[] args) {
		System.out.println("SERVER START at PORT: " + PORT + "!");
		
		// Init Test
		//logger.fatal("log4j: logger.fatal()");
		//logger.error("log4j: logger.error()");
		//logger.warn("log4j: logger.warn()");
		//logger.info("log4j: logger.info()");
		//logger.debug("log4j: logger.debug()");
		//logger.trace("log4j: logger.trace()");

		Reactor reactor = new Reactor(PORT);

		reactor.registerHandler(new StreamSayHelloEventHandler());
		reactor.registerHandler(new StreamUpdateProfileEventHandler());

		reactor.startServer();
	}

}
