package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utility.appconstant.AppConstant.*;

@RestController
@RequestMapping(value = BASE_API_ROUTES)
public class CommentController {
    @Autowired
    private CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @PostMapping(value = COMMENT_POSTMAPPING)
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                                                                                @RequestBody CommentDto commentDto){
        logger.info("CommentController.createComment() : : sending request to service ={}",postId);
        CommentDto commentResponse = commentService.createComment(postId,commentDto);
        logger.info("CommentController.createComment() : : sending response ={}",commentResponse.getId());
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }
    @GetMapping(value = COMMENT_GETALLCOMMENTS)
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable(value = "postId") long postId){
        logger.info("CommentController.getCommentsByPostId() : : sending request to service ={}",postId);
        List<CommentDto> listOfComment = commentService.getAllCommentByPostId(postId);
        logger.info("CommentController.getCommentByPostId() : : sending response");
        return  new ResponseEntity<>(listOfComment,HttpStatus.OK);
    }

    @GetMapping(value = COMMENT_GETCOMMNENTBYID)
    public ResponseEntity<CommentDto> getCommentByPostId(@PathVariable(value = "postId") long postId,
                                                                                                                            @PathVariable(value = "commentId") long commentId){
        logger.info("CommentController.getCommentByPostId() : : sending request to service ={}", postId);
        CommentDto commentResponse = commentService.getCommentByPostId(postId, commentId);
        logger.info("CommentController.getCommentByPostId() : : sending response ={}",commentResponse.getId());
        return new ResponseEntity<>(commentResponse,HttpStatus.OK);
    }

    @PutMapping(value = COMMENT_UPDATE_COMMENT)
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                                                                                    @PathVariable(value = "commentId") long commentId,
                                                                                                                    @RequestBody CommentDto commentReq){
        logger.info("CommentController.updateComment() : :  sending request to service ={}", postId);
        CommentDto commentResponse = commentService.updateComment(postId, commentId,commentReq);
        logger.info("CommentController.updateComment() : : sending response  ={}",commentResponse.getId());
        return new ResponseEntity<>(commentResponse,HttpStatus.OK);
    }
    @DeleteMapping(value = COMMENT_DELETE_COMMENT)
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                                                                  @PathVariable(value = "commentId") long commentId) {
        logger.info("CommentController.updateComment() : : sending request to service ={}", postId);
        commentService.deleteComment(postId, commentId);
        logger.info("CommentController.updateComment() : : sending response ={}", commentId);
        return new ResponseEntity<>(COMMENT_DELETED_SUCCESSFULLY, HttpStatus.OK);
    }
}
