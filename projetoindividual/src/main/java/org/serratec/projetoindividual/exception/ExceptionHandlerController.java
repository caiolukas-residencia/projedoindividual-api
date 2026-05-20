package org.serratec.projetoindividual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    // 400 (o segundo method sendo para regras específicas de negócio, a princípio)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleDadoInvalido(MethodArgumentNotValidException ex) {
        String mensagemDeErro = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErroResposta body = new ErroResposta(mensagemDeErro);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DadoInvalidoException.class)
    public ResponseEntity<ErroResposta> handleDadoInvalidoParticular(DadoInvalidoException ex) {
        ErroResposta body = new ErroResposta(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 404
    @ExceptionHandler(DadoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleDadoNaoEncontrado(DadoNaoEncontradoException ex) {
        ErroResposta body = new ErroResposta(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 409
    @ExceptionHandler(DadoCadastradoException.class)
    public ResponseEntity<ErroResposta> handleDadoCadastrado(DadoCadastradoException ex) {
        ErroResposta body = new ErroResposta(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 500
    @ExceptionHandler(ErroDoServidorException.class)
    public ResponseEntity<ErroResposta> handleErroDoServidor(ErroDoServidorException ex) {
        ErroResposta body = new ErroResposta(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
