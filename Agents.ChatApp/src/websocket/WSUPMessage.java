package websocket;

import java.util.Map;

import com.google.gson.Gson;

public class WSUPMessage {

	private WSUPAction action;
	private int type;
	private String context;
	private String bodyData;

	public static WSUPMessage parseRequest(String message) {
		String[] messageSplit = message.split("\n", 3);

		if (messageSplit.length != 3)
			return null;

		String[] headerSplit = messageSplit[0].split(" ", 2);

		if (headerSplit.length != 2)
			return null;

		if (!headerSplit[0].equals("REQUEST"))
			return null;

		WSUPMessage m = new WSUPMessage();
		m.action = WSUPAction.REQUEST;
		m.type = WSUPRequestType.valueOf(headerSplit[1]).ordinal();
		m.context = messageSplit[1];
		m.bodyData = messageSplit[2];
		return m;
	}

	public <T> T getBodyAs(Class<T> classOfT) {
		return new Gson().fromJson(bodyData, classOfT);
	}

	public WSUPAction getAction() {
		return action;
	}

	public int getType() {
		return type;
	}

	public String getContext() {
		return context;
	}

	@Override
	public String toString() {
		String actionType = "";
		if (action == WSUPAction.REQUEST) {
			actionType = WSUPRequestType.values()[type].toString();
		} else if (action == WSUPAction.RESPONSE) {
			actionType = WSUPResponseType.values()[type].toString();
		}
		return action.toString() + " " + actionType + "\n" + context + "\n" + bodyData;
	}

}
