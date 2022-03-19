package uz.pdp.warehouseapp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import uz.pdp.warehouseapp.entity.User;
import uz.pdp.warehouseapp.repository.UserRepository;

import java.util.Properties;

@Component
public class Dataloader implements CommandLineRunner {
@Value("${spring.jpa.hibernate.ddl-auto}")
private String ddl;
final UserRepository userRepository;

    public Dataloader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       if("create".contains(ddl)||"create-drop".contains(ddl)){
           User user=new User("Sirojiddin","Ismoilov","+998930540905",
                   "sirojiddin.ismoilov7181@gmail.com","12345678");
           user.setCode("WZ:0002");
           userRepository.save(user);
       }
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("javohirbekrakhimov@gmail.com");
        mailSender.setPassword("fgdutjaiuxspzerv");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
