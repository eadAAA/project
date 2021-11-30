//package com.example.profile.kafkaConsumer;
//
//import com.example.profile.models.Users;
//import com.example.profile.repositories.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class Listener {
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @KafkaListener(topics = "first", groupId = "foo")
//    public void listenGroupFoo(String message) {
//        try {
//            String[] messages = message.split(";");
//            Users users = usersRepository.findById(new Long(messages[0])).get();
//            users.setToken(messages[1]);
//            usersRepository.save(users);
//        }catch (Exception e){}
//    }
//}
