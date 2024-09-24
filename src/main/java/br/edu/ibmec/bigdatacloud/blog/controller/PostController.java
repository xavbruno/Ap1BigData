package br.edu.ibmec.bigdatacloud.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.bigdatacloud.blog.model.Post;
import br.edu.ibmec.bigdatacloud.blog.repository.PostRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") int id) {
        /*Optional<Post> postOptional = this.postRepository.findById(id);

        if (postOptional.isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(postOptional.get(), HttpStatus.NOT_FOUND);*/

        return this.postRepository.findById(id)
                                  .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        this.postRepository.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        
        Optional<Post> optPost = this.postRepository.findById(id);

        if (optPost.isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        this.postRepository.delete(optPost.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
