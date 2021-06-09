package pl.irc.frontend;

import pl.irc.dtos.MessageDTO;
import pl.irc.dtos.MessageFormDTO;
import pl.irc.services.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyRestController {

    private final MessagesService messagesService;

    @GetMapping(value = "/messages",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDTO> getMessages() {
        return messagesService.getAllMessages();
    }

    @PostMapping(value = "/messages",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDTO postMessage(@RequestBody MessageFormDTO msg) {
        final MessageDTO dto = new MessageDTO(msg.getText(), "REST API", LocalDateTime.now());
        messagesService.sendNewMessage(dto);
        return dto;
    }

}
