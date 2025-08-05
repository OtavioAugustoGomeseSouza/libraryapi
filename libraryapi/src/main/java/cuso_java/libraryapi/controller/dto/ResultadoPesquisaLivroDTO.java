package cuso_java.libraryapi.controller.dto;

import cuso_java.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(UUID id,
                                        String isbn,
                                        String titulo,
                                        LocalDate dataPublicacao,
                                        GeneroLivro genero,
                                        BigDecimal preco,
                                        AutorDTO autor
                                        ) {
}
