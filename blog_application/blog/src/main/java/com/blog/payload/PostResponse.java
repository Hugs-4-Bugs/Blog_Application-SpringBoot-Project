package com.blog.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
   private List<PostDto> content;
    private int PageNo;
    private int PageSize;
    private long totalElements;
    private int totalPages;

    private boolean isLast;
}
