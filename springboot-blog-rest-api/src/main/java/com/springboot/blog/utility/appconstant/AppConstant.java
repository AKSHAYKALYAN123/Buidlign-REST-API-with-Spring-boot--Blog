package com.springboot.blog.utility.appconstant;

public class AppConstant {
    // API Routes Path
    public static final String BASE_API_ROUTES="/api/v1";
    // Post API
    public static final String POST_POSTMAPPING="/createPost";
    public static final String POST_GETMAPPING="/getAllPosts";
    public static final String POST_GETBYID="/getById/{id}";
    public static final String POST_UPDATEBYID="/updatePostById/{id}";
    public static final String POST_DELETEMAPPING="/deletePostById/{id}";
    // Comment API
    public static final String COMMENT_POSTMAPPING="/createComment/posts/{postId}/comments";
    public static final String COMMENT_GETALLCOMMENTS="/getAllComments/posts/{postId}/comments";
    public static final String COMMENT_GETCOMMNENTBYID="/getCommentById/posts/{postId}/comments/{commentId}";
    public static  final String COMMENT_UPDATE_COMMENT="/updateComment/posts/{postId}/comments/{commentId}";
    public static final String COMMENT_DELETE_COMMENT="/deleteComment/posts/{postId}/comments/{commentId}";


    // Status
    public static final String STATUS_SUCCESS ="Success";
    public static final String STATUS_FAILURE="Failure";


    // Pagination & Sorting
    public static final String PAGE_NUMBER="pageNo";
    public static final String DEFAULT_PAGE_NUMBER="0";
    public static final String PAGE_SIZE="pageSize";
    public static final String DEFAULT_PAGE_SIZE="10";
    public static final String SORT_BY="sortBy";
    public static final String DEFAULT_SORT_BY="id";
    public static final String SORT_DIRECTION= "sortDir";
    public static final String DEFAULT_SORT_DIRECTION="asc";

    // Message
    public static final String COMMENT_DOES_NOT_BELONG_TO_POST="Comment does not belong to post";
    public static final String COMMENT_DELETED_SUCCESSFULLY="Comment deleted  successfully";
    public static final String POST_DELETED_SUCCESSFULLY="Post entity deleted successfully";
    // Constant for Unit Test Case


    public static  final  String EMPTY= "";
    public static final String POST="Post";
    public static final String COMMENT="Comment";

}
