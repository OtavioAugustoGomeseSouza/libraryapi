package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.CadastroLivroDTO;
import cuso_java.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import cuso_java.libraryapi.model.Livro;
import cuso_java.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
