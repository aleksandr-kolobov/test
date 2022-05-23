package main.dto;

import main.model.Message;
import java.time.format.DateTimeFormatter;

public class MessageMapper {
    public static DtoMessage map(Message message){
        DtoMessage dtoMessage = new DtoMessage();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        dtoMessage.setDatetime(message.getDateTime().format(f));
        dtoMessage.setUsername(message.getUser().getName());
        dtoMessage.setText(message.getMessage());
        return dtoMessage;
    }
}
