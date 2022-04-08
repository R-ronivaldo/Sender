package com.example.sender.broker.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import com.example.sender.SendEmail;
import com.example.sender.model.User;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Autowired
	private SendEmail sendEmail;

    @Value("${topic.name.consumer")
    private String topicName;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){
        Map<String, String> myMap = convertPayloadToMap(payload);
        
        User user = new User();
        user.setId(myMap.get("id"));
        user.setUsername(myMap.get("username"));
        user.setEmail(myMap.get("email"));
        
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        sendEmail.sendEmail(user.getEmail(), ""+user.getId()+user.getUsername()+"","Test Java");
    }

    private Map<String, String> convertPayloadToMap(ConsumerRecord<String, String> payload){
        Map<String, String> myMap = new HashMap<String, String>();
        String result = payload.value().replace("{", "").replace("}", "").replace("\"", "");
        System.out.println("result: " + result);
        String[] pairs = result.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            myMap.put(keyValue[0], keyValue[1]);
        }
        return myMap;
    }

}