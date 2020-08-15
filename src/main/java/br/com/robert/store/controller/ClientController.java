package br.com.robert.store.controller;

import br.com.robert.store.dto.ClientDto;
import br.com.robert.store.model.Client;
import br.com.robert.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // Busca utilizando um ID específico. É necessário passar o ID pela URI e passar para o método utilizando
    // o @PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable long id){
        return clientRepository.findById(id)
                .map(client -> ResponseEntity.ok().body(client))
                .orElse(ResponseEntity.notFound().build());
    }

    // Se receber um nome como parâmetro, ele busca um cliente específico
    // Caso contrário, ele retorna uma lista com todos os clientes.
    @GetMapping
    public Object clientList(@RequestParam(required = false) String name){

        if (name == null){
            return clientRepository.findAll();
        } else {
            return clientRepository.findByName(name);
        }
    }

    // Insere um novo cliente
    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody ClientDto clientDto){
        Client client = clientRepository.save(clientDto.convert());
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    // Atualiza o cliente
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

    // Deleta um objeto utilizando o ID,
    // semelhante a consulta, passamos o ID para o método utilizando o @PathVariable
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
