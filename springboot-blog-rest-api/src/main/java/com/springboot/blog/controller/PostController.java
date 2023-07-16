package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.springboot.blog.utility.appconstant.AppConstant.*;

@RestController
@RequestMapping(value = BASE_API_ROUTES)
public class PostController {
    @Autowired
    private PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping(value = POST_POSTMAPPING)
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        logger.info("PostController.createPost(), : : sending request to service {}, ",+postDto.getId());
        PostDto postResponse = postService.createPost(postDto);
        logger.info("PostController.createPost(), : : sending response  {} ",postResponse.getId());
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
    @GetMapping(value = POST_GETMAPPING)
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = PAGE_NUMBER,defaultValue =DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value =PAGE_SIZE, defaultValue =DEFAULT_PAGE_SIZE, required = false ) int pageSize,
            @RequestParam(value=SORT_BY,defaultValue = DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = SORT_DIRECTION,defaultValue = DEFAULT_SORT_DIRECTION,required = false) String sortDir){
        logger.info("PostController.getAllPosts() : : sending request to service");
        PostResponse getAllPosts = postService.getAllPosts(pageNo,pageSize,sortBy, sortDir);
        logger.info("PostController.getAllPosts(), : : sending response ");
        return new ResponseEntity<>(getAllPosts,HttpStatus.OK);
    }
    @GetMapping(value = POST_GETBYID)
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        logger.info("PostController.getPostById() : :  sending request to service{} ",id);
        PostDto postResponse = postService.getPostById(id);
        logger.info("PostController.getPostById() : :  sending response ={}",postResponse.getId());
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @PutMapping(value = POST_UPDATEBYID)
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto  postDto,
                                                                                                    @PathVariable(name = "id") long  id){
        logger.info("PostController.updatePostById() : :  sending request to service ={}",id );
        PostDto postResponse = postService.updatePost(postDto,id);
        logger.info("PostController.getPostById() : :  sending response {}",postResponse.getId());
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    @DeleteMapping(value = POST_DELETEMAPPING)
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        logger.info("PostController.deletePost() : :  sending request to service ={}",id );
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }
}

