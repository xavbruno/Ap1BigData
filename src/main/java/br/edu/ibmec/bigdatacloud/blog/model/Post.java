package br.edu.ibmec.bigdatacloud.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import br.edu.ibmec.bigdatacloud.blog.model.Comment;

@Data
@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String title;
    
    @Column
    private String article;
    
    @Column
    private LocalDateTime dtCreatedDate;
    
    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "post_id")
    private ArrayList<Comment> comments;
}
