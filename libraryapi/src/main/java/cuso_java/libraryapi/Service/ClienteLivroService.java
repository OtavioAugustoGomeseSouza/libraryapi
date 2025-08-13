package cuso_java.libraryapi.Service;

import cuso_java.libraryapi.controller.dto.ClienteLivroDTO;
import cuso_java.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import cuso_java.libraryapi.controller.mappers.LivroMapper;
import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.exceptions.RecursoNaoEncontradoException;
import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.model.Livro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClienteLivroService {

    private final LivroService livroService;
    private final ClienteService clienteService;
    private final LivroMapper livroMapper;


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

    public ClienteLivroDTO buscarCliente(UUID uuid) {
        var cliente = clienteService.obterPorID(uuid);
        if (cliente.isPresent()) {
            Cliente clienteLivro = cliente.get();
            List<Livro> livros = livroService.obterLivrosCliente(cliente.get());
            List<ResultadoPesquisaLivroDTO> livrosDto = new ArrayList<>();
            livros.stream().map(livroMapper::toDTO).forEach(livrosDto::add);

            ClienteLivroDTO clienteLivroDTO = new ClienteLivroDTO(clienteLivro.getId(),
                    clienteLivro.getNome(),
                    clienteLivro.getEmail(),
                    clienteLivro.getEmail(),
                    clienteLivro.getTelefone(),
                    livrosDto);

            return clienteLivroDTO;

        }
        throw new NoSuchElementException("Cliente não encontrado: " + uuid);
    }
}
