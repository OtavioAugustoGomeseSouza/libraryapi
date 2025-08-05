package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.LivroService;
import cuso_java.libraryapi.controller.dto.CadastroLivroDTO;
import cuso_java.libraryapi.controller.dto.ErroReposta;
import cuso_java.libraryapi.controller.mappers.LivroMapper;
import cuso_java.libraryapi.model.Livro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
