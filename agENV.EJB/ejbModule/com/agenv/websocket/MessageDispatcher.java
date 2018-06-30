package com.agenv.websocket;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.beans.WSSessionsBean;
import com.google.gson.Gson;

@Stateless
public class MessageDispatcher {

	@EJB
	private WSSessionsBean wsSessionsBean;
	
	public void broadcast(MessageType type, Object body) {
		String data = type.ordinal() + "|" + new Gson().toJson(body);
		wsSessionsBean.stream().forEach(ses->{
			try {
				ses.getBasicRemote().sendText(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
