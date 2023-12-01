package com.blog.service.impl;

import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.Postrepositories;
import com.blog.service.PostService;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
    public class PostServiceImpl implements PostService {

    public PostServiceImpl(Postrepositories postrepositories) {

        this.postrepositories = postrepositories;
    }

    private Postrepositories postrepositories;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);



        Post savedPost = postrepositories.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;


    }

    @Override
   public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> content = postrepositories.findAll(pageable);
        List<Post>posts = content.getContent();
       List<PostDto>dtos =  posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());
       return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {

       Post post  = postrepositories.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("post not found with id: "+id)


       );

       PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
     Post post =   postrepositories.findById(id).orElseThrow(

             ()->new ResourceNotFoundException("post not found with id :"+id)



        );
          post.setTitle(postDto.getTitle());
          post.setContent(postDto.getContent());
          post.setDescription(postDto.getDescription());



        Post updatedPost = postrepositories.save(post);

        return mapToDto(updatedPost);


    }

    @Override
    public void deletePost(long id) {

        Post post =   postrepositories.findById(id).orElseThrow(

                ()->new ResourceNotFoundException("post not found with id :"+id)



        );
        postrepositories.deleteById(id);


    }

    Post mapToEntity(PostDto postDto){
          Post post = new Post();

          // post.setId(postDto.getId());
          post.setTitle(postDto.getTitle());
          post.setDescription(postDto.getDescription());
          post.setContent(postDto.getContent());
          return post;

      }
      PostDto mapToDto(Post post){
          PostDto dto = new PostDto();
          dto.setId(post.getId());
          dto.setTitle(post.getTitle());
          dto.setDescription(post.getDescription());
          dto.setContent(post.getContent());
          return dto;

      }


    }

