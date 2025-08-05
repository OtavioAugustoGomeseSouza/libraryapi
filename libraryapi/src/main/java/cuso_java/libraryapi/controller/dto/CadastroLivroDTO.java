package cuso_java.libraryapi.controller.dto;

import cuso_java.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "Campo obrigatório")
        @ISBN
        String isbn,

        @NotBlank(message = "Campo obrigatório")
        String titulo,

        @NotNull(message = "Campo obrigatório")
        GeneroLivro genero,

        @Past(message = "Somente são permitidas datas passadas")
        LocalDate dataPublicacao,
        BigDecimal preco,

        @NotNull(message = "Campo obrigatório")
        UUID idAutor) {
}
