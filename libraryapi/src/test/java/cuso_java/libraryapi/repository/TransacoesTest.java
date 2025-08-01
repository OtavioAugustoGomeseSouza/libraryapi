package cuso_java.libraryapi.repository;

import cuso_java.libraryapi.Service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;


    @Test
    void transacaoSimple(){
        transacaoService.executarTransacao();
    }

    @Test
    void transacaoSemSave(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
