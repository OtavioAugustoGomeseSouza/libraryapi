package cuso_java.libraryapi.validator;

import cuso_java.libraryapi.exceptions.CampoInvalidoException;
import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Livro;
import cuso_java.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;
    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if(existeLivroIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preço", "Livros com ano de publicação superior a 2020 devem conter preço");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        if(livro.getPreco() == null){
            return livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
        }

        return false;
    }

    private boolean existeLivroIsbn(Livro livro){
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroOptional.isPresent();

        }

        return livroOptional.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));

    }


}
