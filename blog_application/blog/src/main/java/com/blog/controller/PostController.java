package com.blog.controller;


import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/api/posts" )


public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }
     //http://localhost:8080/api/posts
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?>create(@Valid @RequestBody  PostDto postDto,
                                         BindingResult result){

        if(result.hasErrors())
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);



        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }
     //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(

            @RequestParam(value="pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = "0",required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = "id",required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc",required = false) String sortDir



            ){
              PostResponse postResponse= postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
              return postResponse;



    }
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable("id") long id){

         PostDto dto = postService.getPostById(id);
         return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    //http://localhost:8080/api/posts/{id}
    @PutMapping ("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable("id") long id



    ){
       PostDto dto =  postService.updatePost(postDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    //http://localhost:8080/api/posts/{id}

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id")long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted ", HttpStatus.OK);



    }
}
