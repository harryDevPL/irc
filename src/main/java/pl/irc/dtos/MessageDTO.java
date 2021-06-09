package pl.irc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class MessageDTO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    String text;
    String author;
    @JsonIgnore
    LocalDateTime date;

    public String getDateFormatted() {
        return date.format(FORMATTER);
    }
}
