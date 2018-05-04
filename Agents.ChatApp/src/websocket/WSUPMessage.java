package websocket;

import java.util.Map;

import javax.ws.rs.client.ResponseProcessingException;

import com.google.gson.Gson;

public class WSUPMessage {

	private WSUPAction action;
	private int type;
	private int response;
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
		m.response = -1;
		m.context = messageSplit[1];
		m.bodyData = messageSplit[2];
		return m;
	}

	public WSUPMessage response(WSUPResponseType type, Object data) {
		WSUPMessage response = new WSUPMessage();
		response.action = WSUPAction.RESPONSE;
		response.type = this.type;
		response.response = type.ordinal();
		response.context = context;
		response.bodyData = new Gson().toJson(data);
		return response;
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
			actionType = WSUPRequestType.values()[type].toString();
			actionType += " " + WSUPResponseType.values()[response].toString();
		}
		return action.toString() + " " + actionType + "\n" + context + "\n" + bodyData;
	}

	public String toActionString() {
		// TODO Auto-generated method stub
		String actionType = "";
		if (action == WSUPAction.REQUEST) {
			actionType = WSUPRequestType.values()[type].toString();
		}
		return actionType + "/" + context;
	}

}
