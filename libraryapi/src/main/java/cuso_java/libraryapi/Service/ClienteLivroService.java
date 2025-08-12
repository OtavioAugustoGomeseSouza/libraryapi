package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.exceptions.RecursoNaoEncontradoException;
import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.model.Livro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteLivroService {

    private final LivroService livroService;
    private final ClienteService clienteService;


    @Transactional
    public void emprestar(UUID idCliente, UUID idLivro) {
        var cliente = clienteService.obterPorID(idCliente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + idCliente));

        var livro = livroService.obterPorId(idLivro)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado: " + idLivro));

        if (livro.getCliente() != null) {
            if (livro.getCliente().getId().equals(idCliente)) {
                return;
            }
            throw new OperacaoNaoPermitidaException("O livro já está emprestado para outro cliente.");
        }

        livro.setCliente(cliente);
        livroService.salvar(livro);
    }

    public void devolver(UUID idCliente, UUID idLivro) {
    }
}
