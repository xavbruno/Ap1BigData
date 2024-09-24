package br.edu.ibmec.bigdatacloud.blog.model;

import lombok.*;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column
    @NotBlank(message = "Campo username não pode vazio")
    private String username;

    @Column
    @NotBlank(message = "Campo email não pode vazio")
    @Email(message = "Campo email não está em um formato correto")
    private String email;
    
    @Column
    private String description;
    
    @Column
    private LocalDateTime dtComment;
}
