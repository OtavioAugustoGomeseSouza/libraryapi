package cuso_java.libraryapi.validator;

import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteValidator {

    private ClienteRepository clienteRepository;

    public void validar(Cliente cliente) {
        if (existeClienteCadastrado(cliente)){
            throw new RegistroDuplicadoException("O cliente já está cadastrado");
        }
    }

    private boolean existeClienteCadastrado(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cliente.getCpf());
        if( cliente.getId() == null){
            return clienteOptional.isPresent();
        }

        return clienteOptional.isPresent() && !clienteOptional.get().getId().equals(cliente.getId());
    }
}
