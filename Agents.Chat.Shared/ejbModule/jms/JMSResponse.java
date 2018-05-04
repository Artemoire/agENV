/*package jms;

import java.io.Serializable;
import java.util.HashMap;

public class JMSResponse implements Serializable {

	private HashMap<String, Consumer<WSUPContext>> requestMap;
	
	@EJB
	private WSUserRequestService userService;
	
	public JMSResponse(int status, Object entity) {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	private void init() {
		requestMap = new HashMap<>();
		requestMap.put("login", userService::login);
		// ...
	}
	
	public void handleRequest(WSUPContext context) {
		requestMap.get(context.getMessage().getContext()).accept(context);
	}
}*/
