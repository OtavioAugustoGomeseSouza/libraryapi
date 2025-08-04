package cuso_java.libraryapi.validator;

import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorOptional = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        if (autor.getId() == null){
            return autorOptional.isPresent();
        }else {
            return autorOptional.isPresent() && !autor.getId().equals(autorOptional.get().getId()) ;
        }
    }
}
