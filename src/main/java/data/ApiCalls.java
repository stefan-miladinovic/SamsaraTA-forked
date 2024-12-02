package data;

import utils.PropertiesUtils;

public final class ApiCalls {

    private static final String BASE_URL = PropertiesUtils.getBaseUrl();

    private static final String CHECK_IF_USER_EXISTS = "/api/users/exists/";
    private static final String DELETE_USER = "/api/users/deleteByUsername/";
    private static final String GET_USER = "/api/users/findByUsername/";
    private static final String POST_USER = "/api/users/add";

    public static String createCheckIfUserExistsApiCall(String sUsername) {
        return BASE_URL + CHECK_IF_USER_EXISTS + sUsername;
    }

    public static String createDeleteUserApiCallRelativePath(String sUsername) {
        return DELETE_USER + sUsername;
    }
    public static String createDeleteUserApiCall(String sUsername) {
        return BASE_URL + createDeleteUserApiCallRelativePath(sUsername);
    }

    public static String createGetUserApiCallRelativePath(String sUsername) {
        return GET_USER + sUsername;
    }
    public static String createGetUserApiCall(String sUsername) {
        return BASE_URL + createGetUserApiCallRelativePath(sUsername);
    }

    public static String createPostUserApiCallRelativePath() {
        return POST_USER;
    }
    public static String createPostUserApiCall() {
        return BASE_URL + createPostUserApiCallRelativePath();
    }
}
