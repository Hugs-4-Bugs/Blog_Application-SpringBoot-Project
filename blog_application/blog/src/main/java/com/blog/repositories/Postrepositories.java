package com.blog.repositories;

import com.blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Postrepositories extends JpaRepository<Post,Long> {
}
