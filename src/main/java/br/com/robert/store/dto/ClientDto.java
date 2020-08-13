package br.com.robert.store.dto;

import br.com.robert.store.model.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClientDto {

    private String name;
    private String cpf;

    public ClientDto(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf();
    }

    public Client convert(){
        return new Client(name, cpf);
    }
}
