package cuso_java.libraryapi.repository;

import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

   @Autowired
   AutorRepository repository;

   @Autowired
   LivroRepository livroRepository;

   @Test
   public void salvarTest(){
       Autor autor = new Autor();
       autor.setNome("Juan");
       autor.setDataNascimento(LocalDate.now());
       autor.setNacionalidade("Mexicano");

       var autorSalvo = repository.save(autor);
       System.out.println(autorSalvo);
   }

   @Test
    public void atualizarTest(){

       UUID id = UUID.fromString("8eb147ac-5df7-404e-8272-6e4d5ee5203b");
       Optional<Autor> possivelAutor = repository.findById(id);

       if(possivelAutor.isPresent()){
           Autor autorEncontrado = possivelAutor.get();
            System.out.println("Autor encontrado" + autorEncontrado);

            autorEncontrado.setNacionalidade("Curvelado");
            repository.save(autorEncontrado);


       }else {
           System.out.println("Nenhum Autor encontrado");
       }

   }

   @Test
    public void listarTest(){

        List<Autor> autores = repository.findAll();
        for(Autor autor : autores){
            System.out.println(autor);
        }

   }

   @Test
    public void countTest(){
       System.out.println(repository.count());
   }

   @Test
    public void deletarPorIdTest(){

       Optional autor = repository.findById(UUID.fromString("3dbb77a9-e4a9-423e-a14b-6a4599682244"));

       if (autor.isPresent()){
           repository.deleteById(UUID.fromString("3dbb77a9-e4a9-423e-a14b-6a4599682244"));
       }


   }

   @Test
   public void deletarPorObjeto(){
        Autor autor = repository.findById(UUID.fromString("8eb147ac-5df7-404e-8272-6e4d5ee5203b")).get();
        repository.delete(autor);

   }

   @Test
    public void criarAutorComMultiplosLivrosTest(){
       Autor autor = new Autor();
       autor.setNome("Alice");
       autor.setDataNascimento(LocalDate.now());
       autor.setNacionalidade("Brasileira");

       ArrayList<Livro> livros = new ArrayList<Livro>();

        for (int i=0; i<10; i++){
            Livro livro = new Livro();
            livro.setIsbn("isbn"+i);
            livro.setTitulo("Titulo"+i);
            livro.setPreco(BigDecimal.valueOf(100+i));
            livro.setGenero(GeneroLivro.ROMANCE);
            livro.setDataPublicacao(LocalDate.now());
            livro.setAutor(autor);
            livros.add(livro);
        }

        autor.setLivros(livros);
        repository.save(autor);
        livroRepository.saveAll(livros);
   }

   @Test
    public void buscarLivrosDeAutorTest(){
       Autor autor = repository.findById(UUID.fromString("a357d68d-9fd1-456d-b733-c742d95976c3")).orElse(null);

       autor.setLivros(livroRepository.findByAutor(autor));
       autor.getLivros().forEach(System.out::println);
   }
}
