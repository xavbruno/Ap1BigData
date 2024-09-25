package br.edu.ibmec.bigdatacloud.blog.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ibmec.bigdatacloud.blog.model.Post;
import br.edu.ibmec.bigdatacloud.blog.repository.PostRepository;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired 
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders
                   .webAppContextSetup(context)
                   .build();
    }

    @Test
    public void should_create_post() throws Exception {
        Post post = new Post();
        post.setTitle("Teste");
        post.setArticle("Lorem Ipsum");
        post.setDtCreatedDate(LocalDateTime.now());
        post.setId(10);

        given(this.postRepository.save(post)).willReturn(post);


        this.mvc
            .perform(MockMvcRequestBuilders
                    .post("/post")
                    .content(this.mapper.writeValueAsString(post))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(10)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Teste")));

    }

    @Test
    public void should_get_post() throws Exception {
        Post post = new Post();
        post.setTitle("Teste");
        post.setArticle("Lorem Ipsum");
        post.setDtCreatedDate(LocalDateTime.now());
        post.setId(1);

        //Configurando mock de banco
        given(this.postRepository.findById(1)).willReturn(Optional.of(post));

        this.mvc
            .perform(MockMvcRequestBuilders
                    .get("/post/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(10)));

    }

    @Test
    public void should_get_post_with_not_found() throws Exception {

        //Configurando mock de banco
        given(this.postRepository.findById(1)).willReturn(Optional.empty());

        this.mvc
            .perform(MockMvcRequestBuilders
                    .get("/post/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    
}

