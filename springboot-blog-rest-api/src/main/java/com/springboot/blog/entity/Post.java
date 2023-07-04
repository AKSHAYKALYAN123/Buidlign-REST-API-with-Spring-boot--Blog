package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(name = "title",nullable = false)
   private String tittle;
    @Column(name = "description",nullable = false)
   private String description;
    @Column(name = "content",nullable = false)
   private String content;
}
