package cuso_java.libraryapi.controller.common;

import cuso_java.libraryapi.controller.dto.ErroCampo;
import cuso_java.libraryapi.controller.dto.ErroReposta;
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
}
