package br.edu.ibmec.bigdatacloud.blog.model;

import lombok.*;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;


@Data
@Entity
public class Comment {

    @Id
    private int id;
    
    @Column
    private String username;
    
    @Column
    private String description;
    
    @Column
    private LocalDateTime dtComment;
}
