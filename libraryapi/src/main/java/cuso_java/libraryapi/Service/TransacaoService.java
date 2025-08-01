package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import cuso_java.libraryapi.repository.AutorRepository;
import cuso_java.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void executarTransacao(){
        Autor novoAutor = new Autor();
        novoAutor.setNome("Otávio 2");
        novoAutor.setDataNascimento(LocalDate.now());
        novoAutor.setNacionalidade("Jamaicano");

        //já atualiza o valor no decorrer da transação
        //ideal para quando vc precisa atualizar o valor antes de utiliza-lo novamente
        autorRepository.saveAndFlush(novoAutor);

        Livro novoLivro = new Livro();
        novoLivro.setAutor(novoAutor);
        novoLivro.setDataPublicacao(LocalDate.now());
        novoLivro.setIsbn("novo 2");
        novoLivro.setGenero(GeneroLivro.FANTASIA);
        novoLivro.setTitulo("Novo Livro 2");
        novoLivro.setPreco(new BigDecimal("150.00"));

        livroRepository.save(novoLivro);

        if(novoLivro.getPreco().doubleValue() > 100){
            throw new RuntimeException("Roolback");
        }
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        Livro livro = livroRepository.findById(UUID.fromString("828b655e-f60a-47d9-861c-a9c385b80c2c")).orElse(null);

        livro.setTitulo("Novo Livro ABC");
        //como existe uma transação aberta não precisa do trecho abaixo
        //útil em casos onde eu necessitaria por exemplo de usar o id desse livro dentro do banco na mesma transação
        //livroRepository.save(livro);
    }

}
