package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.repository.ClienteRepository;
import cuso_java.libraryapi.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidator clienteValidator;

    public void salvar(Cliente cliente) {
       // clienteValidator.validar(cliente);
        clienteRepository.save(cliente);
    }
}
