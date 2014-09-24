package basicServer;

import java.util.HashMap;

import eventHandler.EventHandler;

/**
 * @class HandleMap
 * @date 2014-09-24
 * @author min
 * @brief header와 그에 대응하는 event handler를 기억함  
 */
public class HandleMap extends HashMap<String, EventHandler> {
	private static final long serialVersionUID = 1L;

}
