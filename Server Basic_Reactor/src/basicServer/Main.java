package basicServer;

import eventHandler.StreamSayHelloEventHandler;
import eventHandler.StreamUpdateProfileEventHandler;

public class Main {
	public static final int PORT = 5000;

	public static void main(String[] args) {
		System.out.println("SERVER START at PORT: " + PORT + "!");

		Reactor reactor = new Reactor(PORT);

		reactor.registerHandler(new StreamSayHelloEventHandler());
		reactor.registerHandler(new StreamUpdateProfileEventHandler());

		reactor.startServer();
	}

}
