package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Email {
    @Id
    private String email;
    private String code;
    private LocalDateTime expiresAt=LocalDateTime.now();
    private LocalDateTime waitingTime;
    private Integer attempts=3;

    public Email(String email, String code, LocalDateTime waitingTime) {
        this.email = email;
        this.code = code;
        this.waitingTime = waitingTime;
    }

    public Email(String email, LocalDateTime waitingTime, Integer attempts) {
        this.email = email;
        this.waitingTime = waitingTime;
        this.attempts = attempts;
    }
}
