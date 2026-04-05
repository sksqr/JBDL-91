package gfg.notification;

import gfg.com.kafka.UserCreatedPayload;
import gfg.com.kafka.WalletUpdatedPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class NotificationKakfaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(NotificationKakfaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "${user.created.topic}", groupId = "email")
    public void consumeUserCreateTopic(ConsumerRecord payload)  {
        UserCreatedPayload userCreatedPayload = OBJECT_MAPPER.readValue(payload.value().toString(), UserCreatedPayload.class);
        LOGGER.info("Read from kafka : {}", userCreatedPayload);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sk.email.service@gmail.com");
        simpleMailMessage.setSubject("Welcome "+userCreatedPayload.getUserName());
        simpleMailMessage.setText("Hi "+userCreatedPayload.getUserName()+", Welcome in JBDL wallet world");
        simpleMailMessage.setCc("admin.jbdl@yopmail.com");
        simpleMailMessage.setTo(userCreatedPayload.getUserEmail());
        javaMailSender.send(simpleMailMessage);
    }


    @KafkaListener(topics = "${wallet.update.topic}", groupId = "email")
    public void consumeWalletUpdatedTopic(ConsumerRecord payload)  {
        WalletUpdatedPayload walletUpdatedPayload = OBJECT_MAPPER.readValue(payload.value().toString(), WalletUpdatedPayload.class);
        LOGGER.info("Read from kafka : {}", walletUpdatedPayload);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sk.email.service@gmail.com");
        simpleMailMessage.setSubject("Wallet Updated");
        simpleMailMessage.setText("Your JBDL wallet is updated with new balance:"+walletUpdatedPayload.getNewBalance()+" INR");
        simpleMailMessage.setCc("admin.jbdl@yopmail.com");
        simpleMailMessage.setTo(walletUpdatedPayload.getUserEmail());
        javaMailSender.send(simpleMailMessage);
    }
}