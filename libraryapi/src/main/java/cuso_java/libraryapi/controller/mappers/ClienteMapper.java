package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.ClienteDTO;
import cuso_java.libraryapi.controller.dto.ClienteLivroDTO;
import cuso_java.libraryapi.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "id", target = "id")
    Cliente toEntity(ClienteDTO dto);

   @Mapping(source = "id", target = "id")
    ClienteDTO toDto(Cliente cliente);

}
