package cuso_java.libraryapi.validator;

import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Livro;
import cuso_java.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if(existeLivroIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }
    }

    private boolean existeLivroIsbn(Livro livro){
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroOptional.isPresent();

        }

        return livroOptional.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));

    }



}
