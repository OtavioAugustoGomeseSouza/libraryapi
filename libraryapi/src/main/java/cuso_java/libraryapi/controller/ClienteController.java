package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.ClienteService;
import cuso_java.libraryapi.controller.dto.ClienteDTO;
import cuso_java.libraryapi.controller.mappers.ClienteMapper;
import cuso_java.libraryapi.model.Cliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController implements GenericController{

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid ClienteDTO clienteDTO){

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        clienteService.salvar(cliente);

        URI location = gerarHeaderLocation(cliente.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> pesquisar(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "tamanho-pagina", defaultValue = "10") int size,
                                                      @RequestParam(value= "nome", required = false) String nome){
        Page<Cliente> clientes = clienteService.pesquisar(nome, page, size);
        Page<ClienteDTO> clientesDto = clientes.map(item -> clienteMapper.toDto(item));

        return ResponseEntity.ok(clientesDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> obterDetalher(@PathVariable(name = "id") String id){
        Optional<Cliente> cliente = clienteService.obterPorID(UUID.fromString(id));
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }


}
