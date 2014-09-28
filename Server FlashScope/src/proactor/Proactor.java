package proactor;




import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* @brief 프로엑터 방식 서버 클래스
* @details 프로엑터 방식으로 구현된 서버 구동 클래스로
* 헤더와 핸들러를 관리하는 HandleMap과 AsynchronousServerSocketChannel를 관리한다.
* @author flashscope
* @date 2014-05-17
* @version 0.0.1
*/
public class Proactor {
	public static void main(String[] args) {
		
	}
	private int port = 5000;
	private static int threadPoolSize = 30;
	private static int initialSize = 20;
	private static int backlog = 200;
	
	private NioHandleMap handleMap;
	
	/**
     * @brief 프로엑터 생성자
	 * @details 포트번호를 인자로 받아 서버 소켓을 생성할 때 사용한다.
	 * HandleMap을 하나 만들어 둔다.
     * @param port
     */
	public Proactor(int port) {
		this.port = port;
		handleMap = new NioHandleMap();
	}
	
	/**
     * @brief 서버 실행
	 * @details 쓰래드풀의 갯수를 조정할 수 있다.
     */
	public void startServer() {
		serverStarter.ServerInitializer.logger.fatal("Proactor SERVER START!");
		

		// 고정 스레드 풀 생성 threadPoolSize 만큼의 스레드만 사용한다.
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		
		
		try {
			// 캐시 스레드 풀 생성 - 초기에 스레드를 만들고 새 스레드는 필요한 만큼 생성한다. 이전에 만든 스레드를 이용할 수 있다면 재사용할 수도 있다.
			// ExecutorService executor에서 만든 스레드를 AsynchronousChannelGroup이 기본적으로 점유한다.
			// executor의 스레드 숫자는 AsynchronousChannelGroup의 숫자보다 많아야 한다.
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, initialSize);
			
			// 모니터링 스레드 생성
			MyMonitorThread monitor = new MyMonitorThread(executor, 1);
	        Thread monitorThread = new Thread(monitor);
	        monitorThread.start();
	        
			// 스트림 지향의 리스닝 소켓을 위한 비동기 채널
			AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
			listener.bind(new InetSocketAddress(port), backlog);
			
			// 접속의 결과를 CompletionHandler으로 비동기 IO작업에 콜백 형식으로 작업 결과를 받는다.
			listener.accept(listener, new NioDispatcher(handleMap));
			
		} catch (IOException e) {
			e.printStackTrace();
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
		
	}
	
    /**
     * @brief HandleMap에 NioEventHandler등록
	 * @details 헤더와 이벤트 핸들러를 각각 인자로 받아 HandleMap에 등록한다.
     * @param header
     * @param handler
     */
    public void registerHandler(String header, String handler) {
    	handleMap.put(header, handler);
    }
}
