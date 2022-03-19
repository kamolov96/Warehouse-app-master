package uz.pdp.warehouseapp.service;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.dto.UserDTO;
import uz.pdp.warehouseapp.entity.Email;
import uz.pdp.warehouseapp.entity.User;
import uz.pdp.warehouseapp.repository.EmailRepository;
import uz.pdp.warehouseapp.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final EmailSenderService emailSenderService;
    final EmailRepository emailRepository;


    public UserService(UserRepository userRepository, EmailSenderService emailSenderService, EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.emailRepository = emailRepository;
    }

    public Response checkUser(UserDTO userDTO) {
        Response response=new Response();
        Optional<User> userOptional = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        if(userOptional.isPresent()){
            response.setMessage("Successful login");
            response.setSuccess(true);
            return response;
        }
        response.setMessage("Email or password wrong");
        return response;
    }
    public Response setdCodeForRemember(String email) {
        Response response=new Response();
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            Optional<Email> emailCode = emailRepository.findById(email);
            if (emailCode.isPresent()) {
                Email emailDb = emailCode.get();
                LocalDateTime waitingTime = emailDb.getWaitingTime();
                long seconds = Duration.between(LocalDateTime.now(), waitingTime).getSeconds();
                if(seconds>0){
                    response.setMessage("Please wait "+seconds+ " seconds");
                return response;
                }
                User user = byEmail.get();
                try {
                    
                emailSenderService.setMailSender(user.getEmail(),
                        "Get password login for Warehouse", "password:" + user.getPassword());
                emailDb.setWaitingTime(LocalDateTime.now().plusMinutes(5));
                emailRepository.save(emailDb);
                response.setMessage("Check your email");
                response.setSuccess(true);
                return response;
                }catch (MailException e){
                   response.setMessage("Our server something wrong try again");
                return response; 
                }
                
            }else {
                try {
                User user = byEmail.get();
                
                emailSenderService.setMailSender(user.getEmail(),
                        "Get password for login  Warehouse", "password:" + user.getPassword());
                
                Email newEmail= new Email(email,LocalDateTime.now().plusMinutes(5),3);
                emailRepository.save(newEmail);
                response.setMessage("Check your email");
                response.setSuccess(true);
                return response;
                }catch (MailException e){
                   response.setMessage("Our server something wrong try again");
                return response; 
                }
            }
            
        }
        response.setMessage("Not found email");
        return response;
    }

    public Response saveUSer(UserDTO userDTO) {
        String phoneNumber="+998"+userDTO.getOperatorCode()+userDTO.getPhoneNumber();
        Response response=new Response();
        Optional<User> optionalUser = userRepository.findAllByEmailOrPhoneNumber(userDTO.getEmail(), phoneNumber);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getEmail().equals(userDTO.getEmail()))
                response.setMessage(userDTO.getEmail()+" email already exist");
            else
                response.setMessage("This "+userDTO.getPhoneNumber()+" phone number already exist");

                return response;
        }
        User user=new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(), userDTO.getEmail(), userDTO.getPassword());
        User save = userRepository.save(user);
        save.setCode("WZ:000"+save.getId());
        userRepository.save(save);
          try {

              String code=String.valueOf((int)(Math.random()*89999+10000));

            emailSenderService.setMailSender(userDTO.getEmail(),"Verification code",
                    "Code: "+code);
                 Email email=new Email(user.getEmail(),code,LocalDateTime.now().minusMinutes(6));
            emailRepository.save(email);
              response.setMessage("We are send code your email please check it");
          }catch (MailException e){
                response.setMessage("Not found");
                return response;
          }
         response.setSuccess(true);
        return response;
    }

    public Response checkVerificationCode(String email, String code) {
        Response response = new Response();
        Optional<Email> byId = emailRepository.findById(email);
        if (byId.isPresent()) {

            Email emailDb = byId.get();
            LocalDateTime waitingTime = emailDb.getWaitingTime();
            long seconds = Duration.between(LocalDateTime.now(), waitingTime).getSeconds();
            if (seconds > 0) {
                response.setMessage("Please wait " + seconds + " seconds");
                return response;
            }

            if (emailDb.getCode().equals(code)) {
                response.setMessage("Your code correct");
                response.setSuccess(true);
                return response;

            } else {
                Integer attempts = emailDb.getAttempts();
                if (attempts > 0) {
                    emailDb.setAttempts(emailDb.getAttempts() - 1);
                    Email save = emailRepository.save(emailDb);
                    response.setMessage("Wrong code number of remaining attempts: " + attempts);
                    return response;
                } else {

                    userRepository.delete(getByEmail(email));
                    emailRepository.deleteById(email);
                    response.setMessage("Sorry your account deleted");
                    return response;
                }

            }

        }
            response.setMessage("Not found email");
            return response;

    }


    public Response resentVerificationCode(String email) {
        Response response=new Response();
        Optional<Email> byId = emailRepository.findById(email);
        if (byId.isPresent()) {
            Email emailDb = byId.get();
            emailSenderService.setMailSender(emailDb.getEmail(),"Verification code",
                    "Code: "+emailDb.getCode());
             response.setMessage("Check your email");
                response.setSuccess(true);
                return response;
        }
         response.setMessage("Not found email");
            return response;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(new User());
    }
}
