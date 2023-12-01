package com.blog.controller;


import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto>CreateComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDto commentDto





    ){
        CommentDto dto = commentService.saveComment(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //http://localhost:8080/api/posts/1/comments
    @GetMapping ("posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId){


       return commentService.getCommentsByPostId(postId);


    }
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping ("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById
    (@PathVariable("postId") long postId,@PathVariable("commentId")long commentID){

        CommentDto dto = commentService.getCommentById(postId, commentID);

        return new ResponseEntity<>(dto,HttpStatus.OK);


    }
    //http://localhost:8080/api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment
    (@PathVariable("postId")long postId,
     @PathVariable("id") long id,
     @RequestBody CommentDto commentDto






    ){

        CommentDto updatedComment = commentService.updateComment(postId, id, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);



    }

    //http://localhost:8080/api/posts/{postId}/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long Id){
        commentService.deleteComment(postId, Id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK); }



}
