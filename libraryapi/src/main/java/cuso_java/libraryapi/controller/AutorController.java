package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.AutorService;
import cuso_java.libraryapi.controller.dto.AutorDTO;
import cuso_java.libraryapi.controller.mappers.AutorMapper;
import cuso_java.libraryapi.model.Autor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
//http://localhost:8080/autores
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {

        Autor autor = autorMapper.toEntity(dto);
        autorService.salvarAutor(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = autorMapper.toDto(autor);
            return ResponseEntity.ok(autorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();


    }

    @GetMapping
    public ResponseEntity<Page<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {

        Page<Autor> listaAutor = autorService.pesquisaByExample(nome, nacionalidade,page, tamanhoPagina);
        Page<AutorDTO> listaDTO =listaAutor.map((autorMapper::toDto));

        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            System.out.println("não encontrou autor");
            return ResponseEntity.notFound().build();

        } else {
            Autor autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setDataNascimento(autorDTO.dataNascimento());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autorService.atualizar(autor);
            return ResponseEntity.noContent().build();
        }

    }
}
