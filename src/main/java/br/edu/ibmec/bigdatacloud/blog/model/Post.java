package br.edu.ibmec.bigdatacloud.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column
    @NotBlank(message = "Campo titulo não pode ser vazio")
    private String title;
    
    @Column
    @NotBlank(message = "Campo corpo do postagem não pode ser vazio")
    private String article;
    
    @Column
    private LocalDateTime dtCreatedDate;
    
    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "post_id")
    private List<Comment> comments;
}
