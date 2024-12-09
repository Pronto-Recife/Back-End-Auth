package br.com.prontorecife.auth.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExceptionResponseDTO {
    String message;
    Map<String, String> erros;
}