package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.AutorService;
import cuso_java.libraryapi.controller.dto.AutorDTO;
import cuso_java.libraryapi.model.Autor;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
//http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){

        Autor autorEntidade = autor.mapearParaAutor();
        autorService.salvarAutor(autorEntidade);

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
       UUID idAutor = UUID.fromString(id);
       Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
       if(autorOptional.isPresent()){
           Autor autor = autorOptional.get();
           AutorDTO autorDTO = new AutorDTO(
                   autor.getId(),
                   autor.getNome(),
                   autor.getDataNascimento(),
                   autor.getNacionalidade()
           );
           return ResponseEntity.ok(autorDTO);

       }else {
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            autorService.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false)String nacionalidade){

        List<Autor> listaAutor = autorService.pesquisar(nome, nacionalidade);
        List<AutorDTO> listDto = new ArrayList<>();

        for(Autor autor : listaAutor){
            AutorDTO autorDTO = new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            listDto.add(autorDTO);
        }
        return ResponseEntity.ok(listDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id,@RequestBody AutorDTO autorDTO){
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setDataNascimento(autorDTO.dataNascimento());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autorService.atualizar(autor);
            return ResponseEntity.noContent().build();
        }else  {
            return ResponseEntity.notFound().build();
        }
    }
}
