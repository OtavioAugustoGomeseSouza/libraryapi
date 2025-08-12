package cuso_java.libraryapi.controller;

import cuso_java.libraryapi.Service.ClienteLivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes/{idCliente}/livros")
public class ClienteLivroController implements GenericController {

    private final ClienteLivroService clienteLivroService;

    /**
     * Empresta um ÚNICO livro para o cliente.
     * Ex.: POST /clientes/{idCliente}/livros/{idLivro}
     */
    @PostMapping("/{idLivro}")
    public ResponseEntity<Void> emprestarUnico(@PathVariable UUID idCliente,
                                               @PathVariable UUID idLivro) {

        clienteLivroService.emprestar(idCliente, idLivro);
        // Location pode apontar para a listagem dos livros do cliente
        URI location = URI.create("/clientes/" + idCliente + "/livros");
        return ResponseEntity.created(location).build();
    }


    /**
     * (Opcional) Devolver um livro específico.
     * Ex.: PATCH /clientes/{idCliente}/livros/{idLivro}/devolver
     */
    @PatchMapping("/{idLivro}/devolver")
    public ResponseEntity<Void> devolverUnico(@PathVariable UUID idCliente,
                                              @PathVariable UUID idLivro) {
        clienteLivroService.devolver(idCliente, idLivro);
        return ResponseEntity.noContent().build();
    }


}
