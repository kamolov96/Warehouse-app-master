package uz.pdp.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.ClientDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Category;
import uz.pdp.warehouseapp.entity.Client;
import uz.pdp.warehouseapp.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;


    public List<Client> getAll() {
       return (List<Client>) clientRepository.findAll();
    }

    public Response addClient(ClientDto clientDto) {
              Client client = new Client(clientDto.getName(), clientDto.getPhoneNumber(), clientDto.isActive());
              clientRepository.save(client);
              return new Response("added client", true);
    }

    public Client getClientByID(Integer id){
        return clientRepository.findById(id).orElse(new Client());
    }

    public Response updateClient(Client client) {
        Response response=new Response();
        boolean hasName=false;
        for ( Client client1:clientRepository.findAll()) {
            if(client1.getName().trim().toLowerCase().equals(client.getName().
                    trim().toLowerCase())&&!client1.getId().equals(client.getId())){
                hasName=true;
            }
        }
        if(!hasName) {
            clientRepository.save(client);
            response.setSuccess(true);
            response.setMessage("Edite Client");
            return response;
        }
        response.setMessage("This name already exist");
        return response;
    }
}
