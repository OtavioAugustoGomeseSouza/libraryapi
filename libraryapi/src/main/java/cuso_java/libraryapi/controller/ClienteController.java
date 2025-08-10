package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.ClienteService;
import cuso_java.libraryapi.controller.dto.ClienteDTO;
import cuso_java.libraryapi.controller.mappers.ClienteMapper;
import cuso_java.libraryapi.model.Cliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
