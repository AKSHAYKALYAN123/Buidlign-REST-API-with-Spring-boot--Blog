package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.blog.utility.appconstant.AppConstant.POST;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final  Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    @Override
    public PostDto createPost(PostDto postDto) {
        logger.info("PostServiceImpl.createPost() : : converting DTO to Entity  ={}",postDto.getId());
        Post post = mapToEntity(postDto);
        logger.info("PostServiceImpl.createPost() : : save request into database ={}",postDto.getId());
        Post newPost  = postRepository.save(post);
        logger.info("PostServiceImpl.createPost() : : converting Entity to DTO ={}",newPost.getId());
        PostDto postResponse = mapToDto(post);
        logger.info("PostServiceImpl.createPost() : : sending response to controller  ={}",postResponse.getId());
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                                                                                                                                    : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        logger.info("PostServiceImpl.getAllPosts() : : retrieving all post from  database");
        Page<Post> posts = postRepository.findAll(pageable);
        logger.info("PostServiceImpl.getAllPost() : : setting object from Page ={}",posts);
        List<Post> listOfPosts = posts.getContent();
        logger.info("PostServiceImpl.getAllPosts() : : mapping post Entity to DTO");
        List<PostDto> listOfResponse = listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());
        logger.info("PostServiceImpl.getAllPost() : :  setting content in postResponse object ={}",listOfResponse);
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listOfResponse);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        logger.info("PostServiceImpl.getAllPost() : : sending response to controller");
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        logger.info("PostServiceImpl.getPostById() : : retrieving post by id from database ={}",id);
        Post post = retrievingPostById(id);
        logger.info("PostServiceImpl.getPostById() : : retrieving post by id from database ={} ",post.getId());
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        logger.info("PostServiceImpl.updatePost() : : retrieving post by id from database ={}",id);
        Post post = retrievingPostById(id);
        logger.info("PostServiceImpl.updatePost() : : setting request ={}",post.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        logger.info("PostServiceImpl.updatePost() : : saving request into database");
        Post updatePost = postRepository.save(post);
        logger.info("PostServiceImpl.updatePost() : : converting Entity to Map");
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        logger.info("PostServiceImpl.deletePostById() : : deleting post by id  ={}", id);
        postRepository.deleteById(id);
    }

    private  PostDto mapToDto(Post post){
        logger.info("PostServiceImpl.mapToDto() : : Entity to DTO mapped  ={}",post.getId());
        return modelMapper.map(post,PostDto.class);
    }
    private  Post mapToEntity(PostDto postDto){
        logger.info("PostServiceImpl.mapToDto() : : DTO to Entity mapped  ={}",postDto.getId());
        return modelMapper.map(postDto,Post.class);
    }
    public  Post retrievingPostById(long postId) {
        logger.info("PostServiceImpl.retrievingPostById() : : retrieving post by id ={}", postId);
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, "id", postId));
    }
}
