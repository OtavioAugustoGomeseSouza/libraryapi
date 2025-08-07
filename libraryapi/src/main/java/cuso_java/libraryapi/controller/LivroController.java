package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.LivroService;
import cuso_java.libraryapi.controller.dto.CadastroLivroDTO;
import cuso_java.libraryapi.controller.dto.ErroReposta;
import cuso_java.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import cuso_java.libraryapi.controller.mappers.LivroMapper;
import cuso_java.libraryapi.model.GeneroLivro;
import cuso_java.libraryapi.model.Livro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisar(@RequestParam(value = "isbn", required = false) String isbn,
                                                                     @RequestParam(value = "nomeAutor", required = false) String nomeAutor,
                                                                     @RequestParam(value = "titulo", required = false) String titulo,
                                                                     @RequestParam(value = "genero", required = false) GeneroLivro genero,
                                                                     @RequestParam(value = "anoPublicacao", required = false) Integer anoPublicacao){

        List<ResultadoPesquisaLivroDTO> listaDTO= new ArrayList<>();
        List<Livro> livros = livroService.pesquisa(isbn,nomeAutor,titulo,genero,anoPublicacao);

        for (Livro livro : livros) {
            listaDTO.add(livroMapper.toDTO(livro));
        }
        return listaDTO.isEmpty()?ResponseEntity.notFound().build():ResponseEntity.ok(listaDTO);
    }
}
