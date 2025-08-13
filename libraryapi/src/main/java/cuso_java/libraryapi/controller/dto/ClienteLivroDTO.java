package cuso_java.libraryapi.controller.dto;

import cuso_java.libraryapi.model.Cliente;
import cuso_java.libraryapi.model.Livro;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.UUID;

public record ClienteLivroDTO (UUID id,
                              @NotNull
                              @Size(min = 3, max = 100, message = "O nome deve possuir entre 3 e 100 caracteres")
                              String nome,

                              @NotNull
                              @CPF(message = "CPF inválido")
                              String cpf,

                              String email,
                              String telefone,
                               List<ResultadoPesquisaLivroDTO> livrosEmprestados
){
    public Cliente mapearParaCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);

        return cliente;
    }

}
