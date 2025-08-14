package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.UsuarioService;
import cuso_java.libraryapi.controller.dto.UsuarioDTO;
import cuso_java.libraryapi.controller.mappers.UsuarioMapper;
import cuso_java.libraryapi.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}
