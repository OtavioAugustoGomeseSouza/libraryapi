package cuso_java.libraryapi.controller.mappers;

import cuso_java.libraryapi.controller.dto.UsuarioDTO;
import cuso_java.libraryapi.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-15T08:07:24-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setLogin( usuarioDTO.login() );
        usuario.setSenha( usuarioDTO.senha() );
        List<String> list = usuarioDTO.roles();
        if ( list != null ) {
            usuario.setRoles( new ArrayList<String>( list ) );
        }

        return usuario;
    }

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String login = null;
        String senha = null;
        List<String> roles = null;

        login = usuario.getLogin();
        senha = usuario.getSenha();
        List<String> list = usuario.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO( login, senha, roles );

        return usuarioDTO;
    }
}
