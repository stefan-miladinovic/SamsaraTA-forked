package data;

public final class ApiCalls {

    private static final String CHECK_IF_USER_EXISTS = "/api/users/exists/";
    private static final String GET_USER = "/api/users/findByUsername/";
    private static final String POST_USER = "/api/users/add";
    private static final String EDIT_USER = "/api/users/edit";
    private static final String DELETE_USER = "/api/users/deleteByUsername/";
    private static final String CHECK_IF_HERO_EXISTS = "/api/heroes/exists/";
    private static final String GET_HERO ="/api/heroes/findByName/";
    private static final String POST_HERO = "/api/heroes/add";
    private static final String EDIT_HERO = "/api/heroes/edit";
    private static final String DELETE_HERO = "/api/heroes/deleteByName/";

    public static String createCheckIfUserExistsApiCall(String sUsername) {
        return CHECK_IF_USER_EXISTS + sUsername;
    }

    public static String createGetUserApiCall(String sUsername) {
        return  GET_USER + sUsername;
    }

    public static String createPostUserApiCall() {
        return POST_USER;
    }

    public static String createEditUserApiCall() {
        return EDIT_USER;
    }

    public static String createDeleteUserApiCall(String sUsername) {
        return  DELETE_USER + sUsername;
    }

    public static String createCheckIfHeroExistsApiCall(String sHeroName) {
        return  CHECK_IF_HERO_EXISTS + sHeroName;
    }

    public static String createGetHeroApiCall(String sHeroName) {
        return  GET_HERO + sHeroName;
    }

    public static String createPostHeroApiCall() {
        return POST_HERO;
    }

    public static String createEditHeroApiCall() {
        return EDIT_HERO;
    }

    public static String createDeleteHeroApiCall(String sHeroName) {
        return  DELETE_HERO + sHeroName;
    }
}
