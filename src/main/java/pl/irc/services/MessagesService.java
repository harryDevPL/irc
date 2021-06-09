package pl.irc.services;

import pl.irc.dtos.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class MessagesService {

    private final List<MessageDTO> messages = new ArrayList<>();

    @PostConstruct
    public void init() {
        sendNewMessage(new MessageDTO("Siema", "Grześ szedł przez wieś", LocalDateTime.now().minusHours(1)));
        sendNewMessage(new MessageDTO("Druga wiadomość", "Nie ma Grzesia ...", LocalDateTime.now().minusMinutes(10)));
    }

    public List<MessageDTO> getAllMessages() {
        return messages;
    }

    public void sendNewMessage(MessageDTO msg) {
        messages.add(msg);
    }
}
