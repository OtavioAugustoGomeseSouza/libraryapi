package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.LivroService;
import cuso_java.libraryapi.controller.dto.CadastroLivroDTO;
import cuso_java.libraryapi.controller.dto.ErroReposta;
import cuso_java.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import cuso_java.libraryapi.controller.mappers.LivroMapper;
import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {

        //mapear dto para entidade
        Livro livro = livroMapper.toEntity(cadastroLivroDTO);

        //enviar a entidade para o serviçe validar e salvar na base

        livroService.salvar(livro);


        //criar url para acesso dos dados do livro
        var url = gerarHeaderLocation(livro.getId());

        //retornar codigo created com header location
        return ResponseEntity.created(url).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {

        Optional<Livro> livroOptional = livroService.obterPorId(UUID.fromString(id));

        if (livroOptional.isPresent()) {
            ResultadoPesquisaLivroDTO livroDTO = livroMapper.toDTO(livroOptional.get());
            return ResponseEntity.ok(livroDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        Optional<Livro> livroOptional = livroService.obterPorId(UUID.fromString(id));
        if (livroOptional.isPresent()) {
            livroService.deletar(livroOptional.get());
            return  ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisar(@RequestParam(value = "isbn", required = false) String isbn,
                                                                     @RequestParam(value = "nomeAutor", required = false) String nomeAutor,
                                                                     @RequestParam(value = "titulo", required = false) String titulo,
                                                                     @RequestParam(value = "genero", required = false) GeneroLivro genero,
                                                                     @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                     @RequestParam(value = "tamanho-pagina",defaultValue = "10")  Integer tamanhoPagina,
                                                                     @RequestParam(value = "anoPublicacao", required = false) Integer anoPublicacao){


        Page<Livro> livros = livroService.pesquisa(isbn,nomeAutor,titulo,genero,anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> resultado = livros.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Livro> livroOptional = livroService.obterPorId(uuid);

        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
            Livro entity = livroMapper.toEntity(cadastroLivroDTO);

            livro.setTitulo(entity.getTitulo());
            livro.setIsbn(entity.getIsbn());
            livro.setAutor(entity.getAutor());
            livro.setGenero(entity.getGenero());
            livro.setDataPublicacao(entity.getDataPublicacao());
            livro.setPreco(entity.getPreco());

            livroService.atualizar(livro);
            return  ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{idLivro}/devolver")
    public ResponseEntity<Object> devolver(@PathVariable("idLivro") String idLivro){

        UUID id = UUID.fromString(idLivro);
        Optional<Livro> livroOptional = livroService.obterPorId(id);
        if (livroOptional.isPresent()) {
            livroService.devolver(livroOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}
