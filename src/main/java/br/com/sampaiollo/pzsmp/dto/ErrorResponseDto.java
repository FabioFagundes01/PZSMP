package br.com.sampaiollo.pzsmp.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(String message, HttpStatus status) {
}