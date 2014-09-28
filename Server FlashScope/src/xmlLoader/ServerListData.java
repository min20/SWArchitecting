package xmlLoader;

import java.util.List;
import org.simpleframework.xml.ElementList;

/**
* @brief HandlerList데이터에서 server에 관한 노드를 반환한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class ServerListData {

	@ElementList(entry="server", inline=true)
	private List<HandlerListData> server;
	
	public List<HandlerListData> getServer() {
		return server;
	}
}
