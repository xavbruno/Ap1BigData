package br.edu.ibmec.bigdatacloud.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.bigdatacloud.blog.model.Comment;
import br.edu.ibmec.bigdatacloud.blog.model.Post;
import br.edu.ibmec.bigdatacloud.blog.repository.CommentRepository;
import br.edu.ibmec.bigdatacloud.blog.repository.PostRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("post/{id}/comment")
public class CommentController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public ResponseEntity<ArrayList<Comment>> getAll(@PathVariable("id") int idPost) {
        return this.postRepository.findById(idPost)
                                  .map(post -> new ResponseEntity<>(post.getComments(), HttpStatus.OK))
                                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("{idComment}")
    public ResponseEntity<Comment> getById(@PathVariable("idComment") int idComment) {
        return this.commentRepository.findById(idComment)
                                    .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Comment> create(@PathVariable("id") int idPost, @Valid @RequestBody Comment comment) {
        Optional<Post> optPost = this.postRepository.findById(idPost);

        if (optPost.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        Post post = optPost.get();

        //Adiciona o comentário para um determinado post
        post.getComments().add(comment);

        //Salvar na base de dados
        this.commentRepository.save(comment);

        //Liga o post ao comentário
        this.postRepository.save(post);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }

    @DeleteMapping("{idComment}")
    public ResponseEntity delete(@PathVariable("id") int idPost, @PathVariable("idComment") int idComment) {
        Optional<Post> optPost = this.postRepository.findById(idPost);
        Optional<Comment> optComment = this.commentRepository.findById(idComment);

        if (optPost.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (optComment.isPresent() == false) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //Exclui o comentário
        this.commentRepository.delete(optComment.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
