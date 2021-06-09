package pl.irc.frontend;

import pl.irc.dtos.MessageDTO;
import pl.irc.dtos.MessageFormDTO;
import pl.irc.services.MessagesService;
import pl.irc.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ThymeleafController {

    private final MessagesService messagesService;
    private final UsersService usersService;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) {
        model.addAttribute("allMessages", messagesService.getAllMessages());
        model.addAttribute("newMsg", new MessageFormDTO());
        String username = principal != null ? principal.getAttribute("name") : "Anonim";
        final String jsessionId = request.getSession().getId();
        usersService.saveUserInfo(jsessionId, username);
        model.addAttribute("username", username);
        return "index";
    }

    @PostMapping("/newMessage")
    public String newMessage(Model model, @ModelAttribute("newMsg") MessageFormDTO newMsg) {
        final MessageDTO dto = new MessageDTO(newMsg.getText(), "Anonim", LocalDateTime.now());
        messagesService.sendNewMessage(dto);
        return "redirect:/";
    }
}
