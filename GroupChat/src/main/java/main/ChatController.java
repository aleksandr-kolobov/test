package main;

import main.dto.DtoMessage;
import main.dto.MessageMapper;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/init")
    public Map<String, Boolean> init(){
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> optionalUser = userRepository.findBySessionId(sessionId);
        return Map.of("result", optionalUser.isPresent());
    }

    @PostMapping("/auth")
    public Map<String, Boolean> auth(@RequestParam String name){
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = new User();
        user.setName(name);
        user.setSessionId(sessionId);
        userRepository.save(user);
        return Map.of("result", true);
    }

    @PostMapping("/message")
    public Map<String, Boolean> sendMessage(@RequestParam String str){
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> optionalUser = userRepository.findBySessionId(sessionId);
        Message message = new Message();
        message.setMessage(str);
        message.setDateTime(LocalDateTime.now());
        message.setUser(optionalUser.get());
        messageRepository.save(message);
        return Map.of("result", !str.isBlank());
    }

    @GetMapping("/messages")
    public List<DtoMessage> getMessagesList(){
        ArrayList<Message> arrayList = new ArrayList<>();
        for (Message m : messageRepository.findAll()) {
            arrayList.add(m);
        }
        return arrayList.stream().map(m -> MessageMapper.map(m)).collect(Collectors.toList());
    }

    @GetMapping("/users")
    public List<String> getUsersList(){
        ArrayList<User> arrayList = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            arrayList.add(u);
        }
        return arrayList.stream().map(u -> u.getName()).collect(Collectors.toList());
    }

}
