package org.jeecg.modules.devops.compile;

import com.google.common.eventbus.Subscribe;
import org.jeecg.modules.devops.entity.EmailMessage;
import org.jeecg.modules.devops.messages.handle.impl.EmailSendMsgHandle;

import javax.swing.event.ChangeEvent;

public class EventBusTools {

    private static EmailMessage emailMessage = null;
    private static EmailSendMsgHandle emailSendMsgHandle = new EmailSendMsgHandle();
    private static myThread thread = null;

    @Subscribe
    public void sendMailtoDroi(ChangeEvent event) {
        emailMessage = (EmailMessage) event.getSource();
        if (null == thread) {
            thread = new myThread();
            new Thread(thread).start();
        } else {
            new Thread(thread).start();
        }
    }

    class myThread implements Runnable {
        @Override
        public void run() {
            if (emailMessage != null)
            emailSendMsgHandle.SendMsg(emailMessage.getReceiver(), emailMessage.getTitle(), emailMessage.getContent());
            emailMessage = null;
        }
    }
}
