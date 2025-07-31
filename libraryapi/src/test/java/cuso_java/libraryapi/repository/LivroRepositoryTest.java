package cuso_java.libraryapi.repository;

import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("abcde");
        livro.setTitulo("Título do livro");
        livro.setDataPublicacao(LocalDate.now());
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(10.40));

        UUID uuidAutor= UUID.fromString("1411f00f-0b78-4e39-8469-2737ad09e89f");

        Autor autor = autorRepository
                .findById(uuidAutor)
                .orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarComCascata(){

        Livro livro = new Livro();
        livro.setIsbn("novo");
        livro.setTitulo("Novo livro");
        livro.setDataPublicacao(LocalDate.now());
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(10.40));

        UUID uuidAutor= UUID.randomUUID();

        Autor autor = autorRepository
                .findById(uuidAutor)
                .orElse(null);

        //dá pra ver aqui que não foi necessário injetar o repositório do autor aqui Cascata
        if(autor == null) {
            Autor novoAutor = new Autor();
            novoAutor.setNome("Otávio");
            novoAutor.setDataNascimento(LocalDate.now());
            novoAutor.setNacionalidade("Brasileiro");
            livro.setAutor(novoAutor);
            livroRepository.save(livro);
        }else {
          //aqui já usa o autor salvo
            livro.setAutor(autor);
            livroRepository.save(livro);
        }
    }

    @Test
    public void atualizarAutorLivroTest(){
        Livro livro = livroRepository
                .findById(UUID.fromString("f653e806-7af6-4026-afd5-574f69268e87"))
                .orElse(null);

        livro.setAutor(autorRepository
                .getReferenceById(UUID.fromString("f3db4e98-69ba-41d9-8ea2-2ee82f6ded90")));

        livroRepository.save(livro);
    }

    @Test
    @Transactional //sem o transactional não funciona pq o fetch do Many to one está como Lazy em Livro
    public void buscarAutorLivroTest(){
        Autor autor = livroRepository
                .findById(UUID.fromString("f653e806-7af6-4026-afd5-574f69268e87"))
                .orElse(null)
                .getAutor();

        System.out.println(autor);
    }

    @Test
    public void buscarLivroTest(){
        List<Livro> lista = livroRepository.findByGenero(GeneroLivro.ROMANCE);
        lista.forEach(System.out::println);
    }

    @Test
    public void buscarLivroIsnbGeneroTest(){
        List<Livro> lista = livroRepository.findByIsbnAndGenero("isbn5",GeneroLivro.ROMANCE);
        lista.forEach(System.out::println);
    }

    @Test
    public void buscarLivrosOrdenadosTest(){
        List<String> lista = livroRepository.listarTodosPorTitulo();
        for (String nomeLivro : lista){
            System.out.println(nomeLivro);
        }
    }

    @Test
    @Transactional
    public void litarAutoresLivrosTest(){
        List<Autor> autores = livroRepository.ListarAutores();
        for(Autor autor : autores){
            System.out.println(autor);
        }
    }

    @Test
    public void listarLivrosBraileirosTest(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiro();
        System.out.println(resultado);
    }

    @Test
    public void buscarLivrosTest(){
        List<Livro> livros = livroRepository.listarPorGenero(GeneroLivro.ROMANCE);
        for(Livro livro : livros){
            System.out.println(livro);
        }
    }

    @Test
    public void deletarLivroTest(){
        livroRepository.deletarPorGenero(GeneroLivro.CIENCIA);
    }

    @Test
    public void AtualizarLivroTest(){
        livroRepository
                .updateDataPublicacao(UUID.fromString("6f57d06e-7c0a-4e6f-9946-c81697fe75db"),LocalDate.of(2000,1,1));System.out.println("Livros atualizados com sucesso!");
    }
}