package br.com.robert.store.controller;

import br.com.robert.store.dto.ClientDto;
import br.com.robert.store.model.Client;
import br.com.robert.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable long id){
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok().body(client))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Object clientList(@RequestParam(required = false) String name){

        if (name == null){
            return clientRepository.findAll();
        } else {
            return clientRepository.findByName(name);
        }
    }

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody ClientDto clientDto){
        Client client = clientRepository.save(clientDto.convert());
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id,
                                          @RequestBody ClientDto clientDto){

        return clientRepository.findById(id)
                .map(record -> {
                    record.setName(clientDto.getName());
                    record.setCpf(clientDto.getCpf());
                    Client client = clientRepository.save(clientDto.convert());
                    return ResponseEntity.ok().body(client);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") long id){

        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            clientRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
