package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.repository.AutorRepository;
import cuso_java.libraryapi.repository.LivroRepository;
import cuso_java.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator autorValidator;


    public Autor salvarAutor(Autor autor){
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID idAutor){
        return autorRepository.findById(idAutor);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é possível excluir autor com livros cadastrados");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if(nome !=null && nacionalidade !=null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome == null && nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        } else if (nome != null && nacionalidade == null) {
            return autorRepository.findByNome(nome);
        }else {
            return autorRepository.findAll();
        }
    }

    public Page<Autor> pesquisaByExample(String nome, String nacionalidade, Integer pagina, Integer tamanhoPagina){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample= Example.of(autor, matcher);
        Pageable pageRequest = PageRequest.of(pagina,tamanhoPagina);
        return autorRepository.findAll(autorExample, pageRequest);
    }

    public void atualizar(Autor autor){
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
