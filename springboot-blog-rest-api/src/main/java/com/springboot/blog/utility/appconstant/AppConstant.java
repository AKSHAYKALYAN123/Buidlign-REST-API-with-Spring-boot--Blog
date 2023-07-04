package com.springboot.blog.utility.appconstant;

public class AppConstant {

    // API Routes Path
    public static final String BASE_API_ROUTES="/api/v1";
    public static final String POST_POSTMAPPING="/createPost";
    public static final String POST_GETMAPPING="/getAllPosts";
    public static final String POST_GETBYID="/getById/{id}";
    public static final String POST_UPDATEBYID="/updatePostById/{id}";
    public static final String POST_DELETEMAPPING="/deletePostById/{id}";
    // Status
    public static final String STATUS_SUCCESS ="Success";
    public static final String STATUS_FAILURE="Failure";

    // Paginationg & Sorting
    public static final String PAGE_NUMBER="pageNo";
    public static final String DEFAULT_PAGE_NUMBER="0";
    public static final String PAGE_SIZE="pageSize";
    public static final String DEFAULT_PAGE_SIZE="10";
    public static final String SORT_BY="sortBy";
    public static final String DEFAULT_SORT_BY="id";
    public static final String SORT_DIRECTION= "sortDir";
    public static final String DEFAULT_SORT_DIRECTION="asc";

    // Error Message

    // Constant for Unit Test Case


    public static  final  String EMPTY= "";


}
