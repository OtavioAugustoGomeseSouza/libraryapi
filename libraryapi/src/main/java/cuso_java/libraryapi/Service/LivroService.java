package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import cuso_java.libraryapi.repository.LivroRepository;
import cuso_java.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    //isbn, titulo, nome autor, genero e ano da publicação
    public List<Livro> pesquisa(String isbn,
                                String nomeAutor,
                                String titulo,
                                GeneroLivro genero,
                                Integer anoPublicacao){


//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.generoEqual(genero))
//                .and(LivroSpecs.tituloLike(titulo));


        Specification<Livro> specs = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (isbn != null) {
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if (titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar,é necessário que o livro já esteja salvo na base de dados");
        }

        livroRepository.save(livro);
    }
}
