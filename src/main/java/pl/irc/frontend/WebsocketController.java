package pl.irc.frontend;

import pl.irc.dtos.MessageDTO;
import pl.irc.dtos.MessageFormDTO;
import pl.irc.services.MessagesService;
import pl.irc.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final MessagesService messagesService;
    private final UsersService usersService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO send(@Payload MessageFormDTO message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String jsessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        final String username = usersService.getUserInfo(jsessionId);
        final MessageDTO dto = new MessageDTO(message.getText(), username, LocalDateTime.now());
        messagesService.sendNewMessage(dto);
        return dto;
    }
}
