package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.repository.ClienteRepository;
import cuso_java.libraryapi.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidator clienteValidator;

    public void salvar(Cliente cliente) {
       // clienteValidator.validar(cliente);
        clienteRepository.save(cliente);
    }

    public Page<Cliente> pesquisar(String nome, int page, int size) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().
                withIgnoreNullValues().
                withIgnoreCase().
                withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains());


        Example<Cliente> example = Example.of(cliente, exampleMatcher);
        Page<Cliente> clientes = clienteRepository.findAll(example, PageRequest.of(page, size));
        return clientes;
    }

    public Optional<Cliente> obterPorID(UUID uuid) {
        Optional<Cliente> cliente = clienteRepository.findById(uuid);
        return cliente;
    }
}
