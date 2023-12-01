package com.blog.service.impl;


import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.Postrepositories;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private Postrepositories postrepositories;

    private CommentRepository commentRepository;

    public CommentServiceImpl(Postrepositories postrepositories, CommentRepository commentRepository) {
        this.postrepositories = postrepositories;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto) {


        Post post = postrepositories.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)



        );
          Comment comment = mapToEntity(commentDto);
          comment.setPost(post);
          Comment newcomment = commentRepository.save(comment);

         return mapToDto(comment);



    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {


        List<Comment>comments = commentRepository.findByPostId(postId);

       return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());



    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        Post post = postrepositories.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)



        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(

                () -> new ResourceNotFoundException("post not found with id:" + commentId)

        );

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException("Comment does not belongs to post");

        }

            return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        //post Id = 2
        Post post   = postrepositories.findById(postId).orElseThrow(

                () ->new ResourceNotFoundException("Post not found with id:"+postId)



        );
        Comment comment = commentRepository.findById(id).orElseThrow(

                () -> new ResourceNotFoundException("post not found with id:" + id)



        );

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException("Comment does not belongs to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(comment);







    }

    @Override
    public void deleteComment(Long postId, Long id) {
        Post post   = postrepositories.findById(postId).orElseThrow(

                () ->new ResourceNotFoundException("Post not found with id:"+postId)



        );
        Comment comment = commentRepository.findById(id).orElseThrow(

                () -> new ResourceNotFoundException("post not found with id:" + id)



        );

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException("Comment does not belongs to post");
        }

        commentRepository.deleteById(id);




    }

    Comment mapToEntity(CommentDto commentDto ){

        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        return comment;

    }
    CommentDto mapToDto(Comment comment ) {

        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());

        return dto;
    }

}
