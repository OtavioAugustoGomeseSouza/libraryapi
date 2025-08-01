package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository respository) {
        this.autorRepository = respository;
    }

    public Autor salvarAutor(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID idAutor){
        return autorRepository.findById(idAutor);
    }

    public void deletar(Autor autor){
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

    public void atualizar(Autor autor){
        autorRepository.save(autor);
    }
}
