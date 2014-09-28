package reactor;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
* @brief SayHello하는 이벤트 핸들러 
* @details 헤더를 제외한 데이터를 파이프 단위로 2개를 끊어 메시지로 출력을 한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class StreamSayHelloEventHandler implements EventHandler {
	
	private static final int DATA_SIZE = 512;
    private static final int TOKEN_NUM = 2;
    
    /**
     * @brief 이벤트 핸들러의 header를 반환한다.
     * @return header String
     */
	@Override
	public String getHandler() {
		return "0x5001";
	}
	
	/**
     * @brief 데이터를 읽어서 파싱한 후 sayHello 이벤트를 실행한다.
     * @param inputStream
     */
	public void handleEvent(InputStream inputStream) {
		
		try {
			byte[] buffer = new byte[DATA_SIZE];
			inputStream.read(buffer);
			String data = new String(buffer);
			
	        String[] params = new String[TOKEN_NUM];
	        StringTokenizer token = new StringTokenizer(data, "|");
	        
	        int i = 0;
	        while (token.hasMoreTokens()) {
	            params[i] = token.nextToken();
	            ++i;
	        }
	        
	        sayHello(params);
	        
		} catch (IOException e) {
			e.printStackTrace();
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
		
	}
	
	/**
     * @brief 받은 데이터를 콘솔창에 출력한다.
     * @param params
     */
	private void sayHello(String[] params) {
        System.out.println("[REA]SayHello -> name : " + params[0] + " age : " + params[1]);
    }

}
