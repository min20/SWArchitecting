package xmlLoader;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

/**
* @brief HandlerList데이터에서 handler에 관한 데이터들을 리스트형태로 반환한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class HandlerListData {
	
	
	@ElementList(entry="handler", inline=true)
	private List<HandlerData> handler;
	
	@Attribute
	private String name;
	
	
	public List<HandlerData> getHandler() {
		return handler;
	}
	
	public String getName() {
		return name;
	}
	
}
