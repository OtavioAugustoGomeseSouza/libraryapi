package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.ClienteDTO;
import cuso_java.libraryapi.model.Cliente;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-13T16:26:13-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( dto.id() );
        cliente.setNome( dto.nome() );
        cliente.setCpf( dto.cpf() );
        cliente.setEmail( dto.email() );
        cliente.setTelefone( dto.telefone() );

        return cliente;
    }

    @Override
    public ClienteDTO toDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String cpf = null;
        String email = null;
        String telefone = null;

        id = cliente.getId();
        nome = cliente.getNome();
        cpf = cliente.getCpf();
        email = cliente.getEmail();
        telefone = cliente.getTelefone();

        ClienteDTO clienteDTO = new ClienteDTO( id, nome, cpf, email, telefone );

        return clienteDTO;
    }
}
