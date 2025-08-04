package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.AutorService;
import cuso_java.libraryapi.controller.dto.AutorDTO;
import cuso_java.libraryapi.controller.dto.ErroReposta;
import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import cuso_java.libraryapi.model.Autor;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/autores")
//http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            autorService.salvarAutor(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroReposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
        }
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
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
       try {
           UUID idAutor = UUID.fromString(id);
           Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

           if(autorOptional.isEmpty()){
               return ResponseEntity.notFound().build();
           }

           autorService.deletar(autorOptional.get());
           return ResponseEntity.noContent().build();

       }catch (OperacaoNaoPermitidaException e){
           var erroResposta = ErroReposta.respostaPadrao(e.getMessage());
           return ResponseEntity.status(erroResposta.Status()).body(erroResposta);
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
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,@RequestBody AutorDTO autorDTO){
        try {
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
        }catch (RegistroDuplicadoException e){
            var erroDTO = ErroReposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
        }
    }
}
