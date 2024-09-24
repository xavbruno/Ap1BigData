package br.edu.ibmec.bigdatacloud.blog.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ValidationMessageError {
    private String message = "Há erros na sua requisição, verique";
    private List<ValidationError> errors = new ArrayList<>();
}
