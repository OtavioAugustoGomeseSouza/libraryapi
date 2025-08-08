package cuso_java.libraryapi.controller.common;

import cuso_java.libraryapi.controller.dto.ErroCampo;
import cuso_java.libraryapi.controller.dto.ErroReposta;
import cuso_java.libraryapi.exceptions.CampoInvalidoException;
import cuso_java.libraryapi.exceptions.OperacaoNaoPermitidaException;
import cuso_java.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)//Recomendado quando sempre retorna o mesmo status
    public ErroReposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = new ArrayList<>();
        for(FieldError fieldError : fieldErrors){
            ErroCampo erroCampo = new ErroCampo(fieldError.getField(),fieldError.getDefaultMessage());
            erroCampos.add(erroCampo);
        }
        return  new ErroReposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro validação",erroCampos);

    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroReposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroReposta.conflito(e.getMessage());

    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroReposta handleOperacaoNaoPermitida(OperacaoNaoPermitidaException e){
        return ErroReposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroReposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroReposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroReposta handleErrosNaoTratados(RuntimeException e){
        return new ErroReposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com um admin",
                List.of());
    }

}
