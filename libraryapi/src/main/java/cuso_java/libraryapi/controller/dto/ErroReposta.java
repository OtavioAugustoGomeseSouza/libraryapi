package cuso_java.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroReposta(int Status, String mensagem, List<ErroCampo> erros) {

public static ErroReposta respostaPadrao(String mensagem) {
        return new ErroReposta(HttpStatus.BAD_REQUEST.value(), mensagem, null);
    }

    public static ErroReposta conflito(String mensagem) {
        return  new ErroReposta(HttpStatus.CONFLICT.value(), mensagem, null);
    }
}
