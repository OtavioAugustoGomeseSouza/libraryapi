package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.AutorDTO;
import cuso_java.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Se estiver dando erro aqui só comentar os mappers, compilar, descomentar e compilar novamente
@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "idAutor", target = "id")
    Autor toEntity(AutorDTO dto);

    @Mapping(source = "id", target = "idAutor")
    AutorDTO toDto(Autor autor);
}

