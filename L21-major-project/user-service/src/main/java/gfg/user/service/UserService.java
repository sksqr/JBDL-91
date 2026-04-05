package gfg.user.service;


import gfg.com.kafka.UserCreatedPayload;
import gfg.user.dto.UserDto;
import gfg.user.entity.User;
import gfg.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private RedisTemplate<String,UserDto> redisTemplate;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${user.created.topic}")
    private String userCreatedTopic;


    @Transactional
    public long createUser(UserDto userDto) throws ExecutionException, InterruptedException {

        User user = User.builder()
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .kycNumber(userDto.getKycNumber())
                .build();
        user = userRepo.save(user);

        UserCreatedPayload userCreatedPayload = UserCreatedPayload.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .userId(user.getId())
                .build();

        Future<SendResult<String,Object>> future  = kafkaTemplate.
                send(userCreatedTopic, userCreatedPayload.getUserEmail(),userCreatedPayload);
        LOGGER.info("Pushed userCreatedPayload to kafka: {}",future.get());

        String key = "user:"+user.getId();
        LOGGER.info("Putting user details in Redis");
        redisTemplate.opsForValue().set(key,userDto);
        return user.getId();

    }
}
