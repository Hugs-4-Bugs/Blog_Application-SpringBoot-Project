package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

     CommentDto saveComment(Long postId, CommentDto commentDto);
     List<CommentDto> getCommentsByPostId(long postId);
     CommentDto getCommentById(long postId,long commentId);


     CommentDto updateComment(long postId, long id, CommentDto commentDto);

     void deleteComment(Long postId, Long id);
}
