package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.blog.utility.appconstant.AppConstant.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        logger.info("CommentServiceImpl.createComment() : : converting DTO to Entity  ={}", commentDto.getId());
        Comment comment = mapToEntity(commentDto);
        logger.info("CommentServiceImpl.createComment() : : retrieving post by id  ={}", postId);
        Post post = retrievingPostById(postId);
        logger.info("CommentServiceImpl.createComment() : : setting post to comment entity  ={}", post.getId());
        comment.setPost(post);
        logger.info("CommentServiceImpl.createComment() : : submitting comment entity to database ={}", comment.getId());
        Comment commentResponse = commentRepository.save(comment);
        logger.info("CommentServiceImpl.createComment() : : converting entity to DTO ={}", commentResponse.getId());
        CommentDto responseCommentDto = mapToDto(commentResponse);
        logger.info("CommentServiceImpl.createComment() : : sending response to controller ={}", commentResponse.getId());
        return responseCommentDto;
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(long postId) {
        logger.info("CommentServiceImpl.getCommentByPostId() : : retrieve comment by postId ={}", postId);
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        logger.info("CommentServiceImpl.getCommentByPostId() : : convert list of comment entities to DTO");
        List<CommentDto> commentListResponse = comments.stream().map(this::mapToDto).collect(Collectors.toList());
        logger.info("CommentServiceImpl.getCommentByPostId() : : sending response to controller");
        return commentListResponse;
    }

    @Override
    public CommentDto getCommentByPostId(long postId, long commentId) {
        logger.info("CommentServiceImpl.getCommentById() : : retrieving post by postId  ={}", postId);
        Post post = retrievingPostById(postId);
        logger.info("CommentServiceImpl.getCommentById() : : retrieving comment by commentId  ={}", commentId);
        Comment comment = retrievingCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_POST);
        }
        CommentDto commentResponse = mapToDto(comment);
        logger.info("CommentServiceImpl.getCommentById() : : sending response to controller  ={}", comment.getId());
        return commentResponse;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        logger.info("CommentServiceImpl.updateComment() : : retrieving post by postId  ={}", postId);
        Post post = retrievingPostById(postId);
        logger.info("CommentServiceImpl.updateComment() : : retrieving comment by commentId  ={}", commentId);
        Comment comment = retrievingCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_POST);
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        logger.info("CommentServiceImpl.updateComment() : : submitting comment entity to database ={}", comment.getId());
        Comment commentResponse = commentRepository.save(comment);
        CommentDto commentDtoResponse = mapToDto(commentResponse);
        logger.info("CommentServiceImpl.updateComment() : : sending response to controller ={}", comment.getId());
        return commentDtoResponse;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        logger.info("CommentServiceImpl.deleteComment() : : retrieving post by postId  ={}", postId);
        Post post = retrievingPostById(postId);
        logger.info("CommentServiceImpl.deleteComment() : : retrieving comment by commentId ={}", commentId);
        Comment comment = retrievingCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_POST);
        }
        logger.info("CommentServiceImpl.updateComment() : : deleting comment from comment database ={}", commentId);
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        logger.info("CommentServiceImpl.mapToDto() : : Entity to Dto mapped ={}", commentDto.getId());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        logger.info("CommentServiceImpl.mapToEntity() : : Dto to Entity is  mapped ={}", comment.getId());
        return comment;
    }

    public  Post retrievingPostById(long postId) {
        logger.info("CommentServiceImpl.retrievingPostById() : : retrieving post by id ={}", postId);
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, "id", postId));
    }

    public Comment retrievingCommentById(long commentId) {
        logger.info("CommentServiceImpl.retrievingCommentById() : : retrieving comment by id ={}", commentId);
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, "id", commentId));
    }
}