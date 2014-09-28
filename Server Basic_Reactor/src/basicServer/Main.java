package basicServer;

import eventHandler.ReactorFileWriteEventHandler;
import eventHandler.ReactorSayHelloEventHandler;
import eventHandler.ReactorUpdateProfileEventHandler;

/**
 * @class Main
 * @date 2014-09-24
 * @author min
 * @brief main함수를 갖고 있다. reactor를 new하고 실행시킨다.  
 */
public class Main {
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

		reactor.registerHandler(new ReactorSayHelloEventHandler());
		reactor.registerHandler(new ReactorUpdateProfileEventHandler());
		reactor.registerHandler(new ReactorFileWriteEventHandler());

		reactor.startServer();
	}

}
