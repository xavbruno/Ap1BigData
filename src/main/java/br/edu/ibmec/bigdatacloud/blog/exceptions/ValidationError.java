package br.edu.ibmec.bigdatacloud.blog.exceptions;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
}
