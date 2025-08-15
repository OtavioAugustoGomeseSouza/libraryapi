package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Usuario;
import cuso_java.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.findByLogin(usuario.getLogin()) != null) {
            throw new RegistroDuplicadoException("credenciais de login indisponíveis");
        }

        String senha = usuario.getSenha();
        if (senha == null || senha.isBlank()) {
            System.out.println("erro na senha");
            throw new IllegalArgumentException("Senha é obrigatória.");
        }

        usuario.setSenha(encoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
