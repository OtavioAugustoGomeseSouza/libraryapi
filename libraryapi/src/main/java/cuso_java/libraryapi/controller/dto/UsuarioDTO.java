package cuso_java.libraryapi.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UsuarioDTO(
        String login,

        String senha,

        List<String> roles) {
}
