package br.com.sampaiollo.pzsmp.config;

import br.com.sampaiollo.pzsmp.dto.ErrorResponseDto;
import br.com.sampaiollo.pzsmp.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    // Handler para erros de validação (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handler para exceções de regras de negócio (ex: login duplicado)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handler para erros de permissão do Spring Security
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("Acesso negado. Você não tem permissão para acessar este recurso.", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // Handler para recursos não encontrados (ex: cliente com ID inexistente)
    // Este continua genérico, mas agora é usado para casos específicos de "não encontrado".
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(RuntimeException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}