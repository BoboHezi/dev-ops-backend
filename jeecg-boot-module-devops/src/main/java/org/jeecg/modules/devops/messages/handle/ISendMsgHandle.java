package org.jeecg.modules.devops.messages.handle;

public interface ISendMsgHandle {

	void SendMsg(String es_receiver, String es_title, String es_content);
	void SendMsg(String[] es_receiver, String es_title, String es_content);
}
