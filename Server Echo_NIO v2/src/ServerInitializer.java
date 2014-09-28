import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerInitializer {

	private static int PORT = 5000;
	private static int threadPoolSize = 30;
	private static int initialSize = 20;
	private static int backlog = 50;

	public static void main(String[] args) {
		System.out.println("SERVER START at PORT: " + PORT + "!");
		
		NioHandleMap handleMap = new NioHandleMap();
		
		NioEventHandler sayHelloHandler = new NioSayHelloEventHandler();
		NioEventHandler updateProfileHandler = new NioUpdateProfileEventHandler();
		NioEventHandler fileWriteHandler = new NioFileWriteEventHandler();
		
		handleMap.put(sayHelloHandler.getHeader(), sayHelloHandler);
		handleMap.put(updateProfileHandler.getHeader(), updateProfileHandler);
		handleMap.put(fileWriteHandler.getHeader(), fileWriteHandler);
		
		// 고정 스레드 풀 생성. treadPoolSize 만큼의 스레드를 사용한다.
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

		// 캐시 스레드 풀 생성. 초기에 스레드를 만들고 새 스레드는 필요한 만큼 생성한다. 이전 스레드를 이용할 수 있으면 재사용.
		try {
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, initialSize);
			
			// listen socket을 위한 비동기 채널 생성.
			AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
			listener.bind(new InetSocketAddress(PORT), backlog);
			
			// 접속 결과를 비동기 콜백으로 받음.
			listener.accept(listener, new Dispatcher(handleMap));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
