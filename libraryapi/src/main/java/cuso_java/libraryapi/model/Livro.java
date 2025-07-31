package cuso_java.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.apache.logging.log4j.util.Lazy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "livro")
@Data
@ToString(exclude = "autor")
public class Livro {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", length = 30, nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    //cascade é perigoso! pode apagar o autor ao apagar o livro
    @ManyToOne(
            //busca somente dados do livro ao buscar o livro
              fetch = FetchType.LAZY
            //busca os dados do livro e do autor ao buscar o livro
            //fetch = FetchType.EAGER
            //cascade = CascadeType.ALL
            )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GeneroLivro genero;

}
