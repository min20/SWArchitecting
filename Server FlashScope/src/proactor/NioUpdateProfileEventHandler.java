package proactor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.StringTokenizer;

/**
* @brief UpdateProfile하는 이벤트 핸들러 
* @details 헤더를 제외한 데이터를 파이프 단위로 5개를 끊어 메시지로 출력을 한다.
* @author flashscope
* @date 2014-05-17
* @version 0.0.1
*/
public class NioUpdateProfileEventHandler implements NioEventHandler {
	private static final int TOKEN_NUM = 5;
	
	private AsynchronousSocketChannel channel;
	
	/**
     * @brief 이벤트 핸들러의 header를 반환한다.
     * @return header String
     */
	@Override
	public String getHandle() {
		return "0x6001";
	}

	/**
     * @brief 이벤트 핸들러가 요구하는 데이터의 사이즈를 반환한다.
     * @return int dataSize
     */
	@Override
	public int getDataSize() {
		return 1024;
	}
	
	/**
     * @brief 이벤트 핸들러가 필요로 하는 채널과 버퍼 정보를 받는다.
     * @param channel
     * @param buffer
     */
	@Override
	public void initialize(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}
	
	/**
     * @brief 나머지 데이터를 다 읽은 결과로 파싱을한 다음 이벤트를 실행시킨다.
	 * @param result
	 * @param attachment  
     */
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		if (result == -1) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
		} else if (result > 0) {
			buffer.flip();
			String msg = new String(buffer.array());
			
			String[] params = new String[TOKEN_NUM];
			StringTokenizer token = new StringTokenizer(msg, "|");
	        int i = 0;
	        while (token.hasMoreTokens()) {
	            params[i] = token.nextToken();
	            i++;
	        }
	        
	        updateProfile(params);
	        
	        try {
	        	buffer.clear();
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
		}
	}

	/**
     * @brief CompletionHandler가 실패되었을 떄 호출된다.
	 * @param exc
	 * @param listener  
     */
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		serverStarter.ServerInitializer.logger.error("NioUpdateProfileEventHandler Failed");
	}
	
	/**
     * @brief 받은 데이터를 콘솔창에 출력한다.
     * @param params
     */
    private void updateProfile(String[] params) {
        System.out.println("[Pro]UpdateProfile -> " +
                " id :" + params[0] +
                " password : " + params[1] +
                " name : " + params[2] +
                " age : " + params[3] +
                " gender: " + params[4]);
    }

}
