package cuso_java.libraryapi.repository;

import cuso_java.libraryapi.model.Autor;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // QUERY Method

    //Select * From livros where id_autor = "UUID_autor"
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByPreco(BigDecimal preco);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByDataPublicacao(LocalDate dataPublicacao);

    List<Livro> findByGenero(GeneroLivro genero);

    List<Livro> findByIsbnAndGenero(String isbn, GeneroLivro genero);

    List<Livro> findByIsbnOrGenero(String isbn, GeneroLivro genero);

    //JPQL -> referencia as entidades e as propriedades
    @Query("SELECT lv.titulo FROM Livro as lv ORDER BY lv.titulo")
    List<String> listarTodosPorTitulo();

    @Query("SELECT a FROM Livro l join l.autor a ")
    List<Autor> ListarAutores();

    @Query("""
        select l.genero
        from Livro l 
        join l.autor a 
        where a.nacionalidade = 'Brasileiro' 
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiro();

    @Query("select l from Livro l where l.genero = :paramGenero")
    List<Livro> listarPorGenero(@Param("paramGenero") GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("delete from Livro l where l.genero = ?1")
    void deletarPorGenero(GeneroLivro genero);

    @Query("update Livro l set l.dataPublicacao = ?2 where l.id = ?1")
    @Modifying
    @Transactional
    void updateDataPublicacao(UUID id, LocalDate dataPublicacao);

    Boolean existsByAutor(Autor autor);

}
