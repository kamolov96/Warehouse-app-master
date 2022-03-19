package uz.pdp.warehouseapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.dto.UserDTO;

@Controller
@RequestMapping(path = "/")
public class Main {
    @GetMapping
    public String loginPage(Model model){
        model.addAttribute("userDto",new UserDTO());
        model.addAttribute("message",new Response());
        return "/user/login";
    }
}
