package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.UsuarioDTO;
import cuso_java.libraryapi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "roles", source = "roles")
    Usuario toEntity(UsuarioDTO usuarioDTO);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "roles", source = "roles")
    UsuarioDTO toDTO(Usuario usuario);
}
