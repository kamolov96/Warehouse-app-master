package uz.pdp.warehouseapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.dto.UserDTO;
import uz.pdp.warehouseapp.service.UserService;


@Controller
@RequestMapping(path = "/warehouse/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, Model model) {
        Response response = userService.checkUser(userDTO);
        model.addAttribute("message", response);
        if (response.isSuccess())
            return "dashboard";
        else

        return "redirect:/";
    }

    @GetMapping(path = "/code")
    public String emailInput(Model model) {
        model.addAttribute("message", new Response("Enter your email", true));
        return "/user/emailInput";
    }

    @PostMapping(path = "/code")
    public String sendCode(Model model, @ModelAttribute("email") String email) {
        Response response = userService.setdCodeForRemember(email);
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("message", response);
        return "/user/login";

    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("message", new Response());
        return "/user/register";
    }

    @PostMapping(path = "/register")
    public String saveNewUser(UserDTO userDTO, Model model) {
        Response response = userService.saveUSer(userDTO);
        if (response.isSuccess()) {
            model.addAttribute("userDto", userDTO);
            model.addAttribute("message", response);
            return "/user/verficationCode";
        }
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("message", response);
        return "/user/register";
    }

    @PostMapping(path = "/verification/{email}")
    public String register(@PathVariable String email, Model model,
                           @ModelAttribute("code") String code) {
        Response response = userService.checkVerificationCode(email, code);

        if (response.isSuccess()) {
            model.addAttribute("message", response);
            return "dashboard";
        }
        model.addAttribute("userDto", userService.getByEmail(email));
        model.addAttribute("message", response);
        if (response.getMessage().equals("Sorry your account deleted")) {
            model.addAttribute("userDto", new UserDTO());
            model.addAttribute("message", response);
            return "/user/register";
        }

        return "/user/verficationCode";
    }

    @GetMapping(path = "/verification/resent/{email}")
    public String resentVerificationCode(@PathVariable String email, Model model) {
        Response response = userService.resentVerificationCode(email);
        model.addAttribute("message", response);
        model.addAttribute("userDto", userService.getByEmail(email));
        return "/user/verficationCode";
    }
}