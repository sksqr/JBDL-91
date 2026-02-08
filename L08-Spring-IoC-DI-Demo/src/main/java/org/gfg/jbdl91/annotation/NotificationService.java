package org.gfg.jbdl91.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
//@Component // directly OR indirectly
public class NotificationService {

    @Autowired // field based inject
    private SMSGatewayService smsGatewayService;

//    public NotificationService(SMSGatewayService smsGatewayService) {
//        System.out.println("Executing NotificationService constructor");
//        this.smsGatewayService = smsGatewayService;
//    }

    public void sendNotification(String msg){
        smsGatewayService.sendSMS(msg);
    }

    public SMSGatewayService getSmsGatewayService() {
        return smsGatewayService;
    }

    //@Autowired // setter based injection
    public void setSmsGatewayService(SMSGatewayService smsGatewayService) {
        this.smsGatewayService = smsGatewayService;
    }
}
