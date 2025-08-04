package cuso_java.libraryapi.controller.dto;

import cuso_java.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID idAutor,
                       @NotBlank(message = "campo obrigatório")
                       @Size(min = 2, max = 100, message = "O campo deve possuir entre 2 e 100 caracteres")
                       String nome,

                       @NotNull(message = "campo obrigatório")
                       @Past(message = "Não pode ser uma data futura")
                       LocalDate dataNascimento,

                       @NotBlank(message = "campo obrigatório")
                       @Size(min = 5, max = 100, message = "O campo deve possuir entre 5 e 100 caracteres")
                       String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }

}
