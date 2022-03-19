package uz.pdp.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.warehouseapp.dto.CategoryDto;
import uz.pdp.warehouseapp.dto.ClientDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Category;
import uz.pdp.warehouseapp.entity.Client;
import uz.pdp.warehouseapp.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/warehouse/client")
public class ClientController {
    @Autowired
    ClientService clientService;
    @GetMapping()
    public String showClient(Model model){
        model.addAttribute("clientDto",new ClientDto());
        List<Client>client=clientService.getAll();
        List<Client> clients = client.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());
        model.addAttribute("clients",clients);
        List<Client> chooseList = clients.stream().filter(Client::isActive).collect(Collectors.toList());
        model.addAttribute("clientChoose",chooseList);


        if(clients.isEmpty()){
            model.addAttribute("message",new Response("Not found any client",false));
        }else
            model.addAttribute("message",new Response("Total client amount: "+clients.size(),true));
        return "/client/clientOperation";
    }
    @PostMapping(path = "/add")
    public String addClient(ClientDto clientDto,Model model){
        Response response=clientService.addClient(clientDto);
        model.addAttribute("clientDto",new ClientDto());
        List<Client> clients=clientService.getAll();
        List<Client> collect = clients.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("clients",collect);
        List<Client> chooseList = clients.stream().filter(Client::isActive).collect(Collectors.toList());
        model.addAttribute("clientChoose",chooseList);
        model.addAttribute("message",response);
        return "/client/clientOperation";
        }


    @GetMapping(path = "/edite/{id}")
    public String editeCategory(@PathVariable Integer id, Model model){

        Client client= clientService.getClientByID(id);
        List<Client> clients=clientService.getAll();
        List<Client> collect = clients.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("clients",collect);
        List<Client> chooseList = clients.stream().filter(Client::isActive).collect(Collectors.toList());
        chooseList.remove(client);
        model.addAttribute("clientsChoose",chooseList);
        if(clients.isEmpty()){

            model.addAttribute("message",new Response("Not found this client",false));
        }else {
            model.addAttribute("client",client);
        }
        model.addAttribute("message",new Response());
        return "/client/editeClient";
    }
    @PostMapping(path = "/edite/{id}")
    public String updateCategory(Client client,Model model){
        Response response=clientService.updateClient(client);
        if(response.isSuccess()){
            model.addAttribute("clientDto",new ClientDto());
            List<Client> clients=clientService.getAll();
            List<Client> collect = clients.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            model.addAttribute("clients ",collect);
            List<Client> chooseList = clients.stream().filter(Client::isActive).collect(Collectors.toList());
            chooseList.remove(client);
            model.addAttribute("clientsChoose",chooseList);
            model.addAttribute("message",response);

            return "redirect:/warehouse/client";
        }
        Client clientReturn= clientService.getClientByID(client.getId());
        List<Client> clients=clientService.getAll();
        List<Client> collect = clients.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("clients",collect);
        List<Client> chooseList = clients.stream().filter(Client::isActive).collect(Collectors.toList());
        chooseList.remove(client);
        model.addAttribute("clientsChoose",chooseList);
        if(clients.isEmpty()){

            model.addAttribute("message",new Response("Not found this client",false));
        }else {
            model.addAttribute("client",clientReturn);
        }
        model.addAttribute("message",new Response());
        return "/client/editeClient";
    }
    


}
