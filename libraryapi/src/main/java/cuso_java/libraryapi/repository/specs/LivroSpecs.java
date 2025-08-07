package cuso_java.libraryapi.repository.specs;

import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return  ((root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("titulo")),"%" + titulo.toUpperCase() + "%" ));
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("genero"), genero));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal( criteriaBuilder.function(
                        "to_char", String.class, root.get("dataPublicacao"),criteriaBuilder.literal("YYYY")) , anoPublicacao.toString()));
    }

    public static Specification<Livro> nomeAutorLike(String nome){

//        return  (root, query, criteriaBuilder)
//                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")),"%" + nome.toUpperCase() + "%" );

        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);

            return criteriaBuilder.like(joinAutor.get("nomeAutor"),"%" + nome + "%");
        };
    }
}
