package br.com.prontorecife.auth.Exceptions;

import br.com.prontorecife.auth.DTOs.ExceptionResponseDTO;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedInSecurity(BaseException exception){
        ProblemDetail probD = exception.problemDetail();
        return ResponseEntity.status(probD.getStatus()).body(probD);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDTO> handleCustomException(CustomException ex) {
        ExceptionResponseDTO response = new ExceptionResponseDTO();
        response.setMessage(ex.getMessage());
        response.setErros(ex.getErrors());
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }
}
