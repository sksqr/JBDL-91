package org.gfg.jbdl91.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SMSGatewayService {

    public SMSGatewayService() {
        System.out.println("Executing SMSGatewayService constructor");
    }

    public void sendSMS(String msg){
        System.out.println("Sending:"+msg);
    }
}
