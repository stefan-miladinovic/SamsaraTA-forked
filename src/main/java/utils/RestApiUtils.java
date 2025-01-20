package utils;

import com.google.gson.Gson;
import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.ApiError;
import objects.Hero;
import objects.User;
import org.testng.Assert;
import pages.BasePageClass;

import java.util.List;

public class RestApiUtils extends LoggerUtils {

    private static final String BASE_URL = BasePageClass.getBaseUrl();
    private static final String sAdminUser = PropertiesUtils.getAdminUsername();
    private static final String sAdminPass = PropertiesUtils.getAdminPassword();


    // *******************************
    // ********** USERS API **********
    // *******************************

    // --------------------------------
    // ----- CHECK IF USER EXISTS -----
    // --------------------------------

    private static Response checkIfUserExistsApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createCheckIfUserExistsApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in checkIfUserExists(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Check if User with specified Username Exists
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {boolean} Whether User exists or not
     */
    public static boolean checkIfUserExists(String sUsername, String sAuthUser, String sAuthPass) {
        log.trace("checkIfUserExists(" + sUsername + ")");
        Response response = checkIfUserExistsApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in checkIfUserExists(" + sUsername + ") Api Call! Response Body: " + sResponseBody);
        if (!(sResponseBody.equals("true") || sResponseBody.equals("false"))) {
            Assert.fail("Cannot convert response body '" + sResponseBody + "' to boolean value! Response Body: " + sResponseBody);
        }
        return Boolean.parseBoolean(sResponseBody);
    }

    /**
     * API Call: Check if User with specified Username Exists
     *
     * @param sUsername {String} Username
     * @return {boolean} Whether User exists or not
     */
    public static boolean checkIfUserExists(String sUsername) {
        return checkIfUserExists(sUsername, sAdminUser, sAdminPass);
    }


    // --------------------
    // ----- GET USER -----
    //---------------------

    private static Response getUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createGetUserApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in getUser(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Get User with specified Username
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} User details in Json format
     */
    public static String getUserJsonFormat(String sUsername, String sAuthUser, String sAuthPass) {
        Assert.assertTrue(checkIfUserExists(sUsername, sAuthUser, sAuthPass), "User '" + sUsername + "' doesn't exist!");
        Response response = getUserApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in getUser(" + sUsername + ") Api Call! Response Body: " + sResponseBody);
        return response.getBody().asPrettyString();
    }

    /**
     * API Call: Get User with specified Username
     *
     * @param sUsername {String} Username
     * @return {String} User details in Json format
     */
    public static String getUserJsonFormat(String sUsername) {
        return getUserJsonFormat(sUsername, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Get User with specified Username
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {User} User object with all user details
     */
    public static User getUser(String sUsername, String sAuthUser, String sAuthPass) {
        log.debug("getUser(" + sUsername + ")");
        String json = getUserJsonFormat(sUsername, sAuthUser, sAuthPass);
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    /**
     * API Call: Get User with specified Username
     *
     * @param sUsername {String} Username
     * @return {User} User object with all user details
     */
    public static User getUser(String sUsername) {
        return getUser(sUsername, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Get List of Heroes of User with specified Username
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {List<Hero>} List of specified User's Heroes
     */
    public static List<Hero> getUserHeroes(String sUsername, String sAuthUser, String sAuthPass) {
        log.debug("getUserHeroes(" + sUsername + ")");
        User user = getUser(sUsername, sAuthUser, sAuthPass);
        return user.getHeroes();
    }

    public static List<Hero> getUserHeroes(String sUsername) {
        return getUserHeroes(sUsername, sAdminUser, sAdminPass);
    }


    // ---------------------
    // ----- POST USER -----
    // ---------------------

    private static Response postUserApiCall(User user, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createPostUserApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user, User.class);
            // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .body(json)
                    .when().redirects().follow(false)
                    .post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in postUser(" + user.getUsername() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Post User
     *
     * @param user      {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void postUser(User user, String sAuthUser, String sAuthPass) {
        log.debug("postUser(" + user.getUsername() + ")");
        Assert.assertFalse(checkIfUserExists(user.getUsername(), sAuthUser, sAuthPass), "User '" + user.getUsername() + "' already exists!");
        Response response = postUserApiCall(user, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in postUser(" + user.getUsername() + ") Api Call! Response Body: " + sResponseBody);
        log.debug("User Created: " + checkIfUserExists(user.getUsername(), sAuthUser, sAuthPass));
    }

    /**
     * API Call: Post User
     *
     * @param user {User} User
     */
    public static void postUser(User user) {
        postUser(user, sAdminUser, sAdminPass);
    }


    // ---------------------
    // ----- EDIT USER -----
    // ---------------------

    private static Response editUserApiCall(User user, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createEditUserApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user, User.class);
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .body(json)
                    .when().redirects().follow(false)
                    .put(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in editUser(" + user.getUsername() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Edit User
     *
     * @param user      {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void editUser(User user, String sAuthUser, String sAuthPass) {
        log.debug("editUser(" + user.getUsername() + ")");
        Assert.assertTrue(checkIfUserExists(user.getUsername(), sAuthUser, sAuthPass), "User '" + user.getUsername() + "' doesn't exist!");
        Response response = editUserApiCall(user, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in editUser(" + user.getUsername() + ") Api Call! Response Body: " + sResponseBody);
    }

    /**
     * API Call: Edit User
     *
     * @param user {User} User
     */
    public static void editUser(User user) {
        editUser(user, sAdminUser, sAdminPass);
    }


    // -----------------------
    // ----- DELETE USER -----
    // -----------------------

    private static Response deleteUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createDeleteUserApiCall(sUsername);
        Response response = null;
        try {

            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().redirects().follow(false)
                    .delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in deleteUser(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Delete User with specified Username
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void deleteUser(String sUsername, String sAuthUser, String sAuthPass) {
        log.debug("deleteUser(" + sUsername + ")");
        Assert.assertTrue(checkIfUserExists(sUsername, sAuthUser, sAuthPass), "User '" + sUsername + "' doesn't exist!");
        Response response = deleteUserApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in deleteUser(" + sUsername + ") Api Call! Response Body: " + sResponseBody);
        log.debug("User Deleted: " + !checkIfUserExists(sUsername, sAuthUser, sAuthPass));
    }

    /**
     * API Call: Delete User with specified Username
     *
     * @param sUsername {String} Username
     */
    public static void deleteUser(String sUsername) {
        deleteUser(sUsername, sAdminUser, sAdminPass);
    }


    // **************************************
    // ********** USERS API ERRORS **********
    // **************************************

    // --------------------------
    // ----- GET USER ERROR -----
    //---------------------------

    /**
     * API Call: Get User Error
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String getUserErrorJsonFormat(String sUsername, String sAuthUser, String sAuthPass) {
        Response response = getUserApiCall(sUsername, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Get User Error
     *
     * @param sUsername {String} Username
     * @return {String} ApiError details in Json format
     */
    public static String getUserErrorJsonFormat(String sUsername) {
        return getUserErrorJsonFormat(sUsername, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Get User Error
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError getUserError(String sUsername, String sAuthUser, String sAuthPass) {
        log.debug("getUserError(" + sUsername + ")");
        String json = getUserErrorJsonFormat(sUsername, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Get User Error
     *
     * @param sUsername {String} Username
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError getUserError(String sUsername) {
        return getUserError(sUsername, sAdminUser, sAdminPass);
    }


    // ---------------------------
    // ----- POST USER ERROR -----
    //----------------------------

    /**
     * API Call: Post User Error
     *
     * @param user {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String postUserErrorJsonFormat(User user, String sAuthUser, String sAuthPass) {
        Response response = postUserApiCall(user, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Post User Error
     *
     * @param user {User} User
     * @return {String} ApiError details in Json format
     */
    public static String postUserErrorJsonFormat(User user) {
        return postUserErrorJsonFormat(user, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Post User Error
     *
     * @param user {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError postUserError(User user, String sAuthUser, String sAuthPass) {
        log.debug("postUserError(" + user.getUsername() + ")");
        String json = postUserErrorJsonFormat(user, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Post User Error
     *
     * @param user {User} User
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError postUserError(User user) {
        return postUserError(user, sAdminUser, sAdminPass);
    }


    // ---------------------------
    // ----- EDIT USER ERROR -----
    //----------------------------

    /**
     * API Call: Edit User Error
     *
     * @param user {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String editUserErrorJsonFormat(User user, String sAuthUser, String sAuthPass) {
        Response response = editUserApiCall(user, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Edit User Error
     *
     * @param user {User} User
     * @return {String} ApiError details in Json format
     */
    public static String editUserErrorJsonFormat(User user) {
        return editUserErrorJsonFormat(user, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Edit User Error
     *
     * @param user {User} User
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError editUserError(User user, String sAuthUser, String sAuthPass) {
        log.debug("editUserError(" + user.getUsername() + ")");
        String json = editUserErrorJsonFormat(user, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Edit User Error
     *
     * @param user {User} User
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError editUserError(User user) {
        return editUserError(user, sAdminUser, sAdminPass);
    }


    // -----------------------------
    // ----- DELETE USER ERROR -----
    //------------------------------

    /**
     * API Call: Delete User Error
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String deleteUserErrorJsonFormat(String sUsername, String sAuthUser, String sAuthPass) {
        Response response = deleteUserApiCall(sUsername, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Delete User Error
     *
     * @param sUsername {String} Username
     * @return {String} ApiError details in Json format
     */
    public static String deleteUserErrorJsonFormat(String sUsername) {
        return deleteUserErrorJsonFormat(sUsername, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Delete User Error
     *
     * @param sUsername {String} Username
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError deleteUserError(String sUsername, String sAuthUser, String sAuthPass) {
        log.debug("deleteUserError(" + sUsername + ")");
        String json = deleteUserErrorJsonFormat(sUsername, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Delete User Error
     *
     * @param sUsername {String} Username
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError deleteUserError(String sUsername) {
        return deleteUserError(sUsername, sAdminUser, sAdminPass);
    }


    // ********************************
    // ********** HEROES API **********
    // ********************************

    // --------------------------------
    // ----- CHECK IF HERO EXISTS -----
    // --------------------------------

    private static Response checkIfHeroExistsApiCall(String sHeroName, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createCheckIfHeroExistsApiCall(sHeroName);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in checkIfHeroExists(" + sHeroName + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Check if Hero with specified HeroName Exists
     *
     * @param sHeroName {String} HeroName
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {boolean} Whether Hero exists or not
     */
    public static boolean checkIfHeroExists(String sHeroName, String sAuthUser, String sAuthPass) {
        log.trace("checkIfHeroExists(" + sHeroName + ")");
        Response response = checkIfHeroExistsApiCall(sHeroName, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResult = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in checkIfHeroExists(" + sHeroName + ")! Response Body: " + sResult);
        return Boolean.parseBoolean(sResult);
    }

    /**
     * API Call: Check if Hero with specified HeroName Exists
     *
     * @param sHeroName {String} HeroName
     * @return {boolean} Whether Hero exists or not
     */
    public static boolean checkIfHeroExists(String sHeroName) {
        return checkIfHeroExists(sHeroName, sAdminUser, sAdminPass);
    }


    // --------------------
    // ----- GET HERO -----
    // --------------------

    private static Response getHeroApiCall(String sHeroName, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createGetHeroApiCall(sHeroName);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in getHero(" + sHeroName + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Get Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} Hero details in Json format
     */
    public static String getHeroJsonFormat(String sHeroName, String sAuthUser, String sAuthPass) {
        Assert.assertTrue(checkIfHeroExists(sHeroName, sAuthUser, sAuthPass), "Hero '" + sHeroName + "' doesn't exist!");
        Response response = getHeroApiCall(sHeroName, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in getHero(" + sHeroName + ") Api Call! Response Body: " + response.getBody().toString());
        return response.getBody().asString();
    }

    /**
     * API Call: Get Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     * @return {String} Hero details in Json format
     */
    public static String getHeroJsonFormat(String sHeroName) {
        return getHeroJsonFormat(sHeroName, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Get Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {Hero} Hero object with all hero details
     */
    public static Hero getHero(String sHeroName, String sAuthUser, String sAuthPass) {
        log.debug("getHero(" + sHeroName + ")");
        String json = getHeroJsonFormat(sHeroName, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, Hero.class);
    }

    /**
     * API Call: Get Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     * @return {Hero} Hero object with all hero details
     */
    public static Hero getHero(String sHeroName) {
        return getHero(sHeroName, sAdminUser, sAdminPass);
    }


    // ---------------------
    // ----- POST HERO -----
    // ---------------------

    private static Response postHeroApiCall(Hero hero, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createPostHeroApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(hero, Hero.class);
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .body(json)
                    .when().redirects().follow(false)
                    .post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in postHero(" + hero.getHeroName() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Post Hero
     *
     * @param hero      {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void postHero(Hero hero, String sAuthUser, String sAuthPass) {
        log.debug("postHero(" + hero.getHeroName() + ")");
        Assert.assertFalse(checkIfHeroExists(hero.getHeroName(), sAuthUser, sAuthPass), "Hero '" + hero.getHeroName() + "' already exists!");
        Response response = postHeroApiCall(hero, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in postHero(" + hero.getHeroName() + ") Api Call! Response Body: " + sResponseBody);
        log.debug("Hero Created: " + checkIfHeroExists(hero.getHeroName(), sAuthUser, sAuthPass));
    }

    /**
     * API Call: Post Hero
     *
     * @param hero {Hero} Hero
     */
    public static void postHero(Hero hero) {
        postHero(hero, sAdminUser, sAdminPass);
    }


    // ---------------------
    // ----- EDIT HERO -----
    // ---------------------

    private static Response editHeroApiCall(Hero hero, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createEditHeroApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(hero, Hero.class);
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .body(json)
                    .when().redirects().follow(false)
                    .put(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in editHero(" + hero.getHeroName() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Edit Hero
     *
     * @param hero      {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void editHero(Hero hero, String sAuthUser, String sAuthPass) {
        log.debug("editHero(" + hero.getHeroName() + ")");
        Assert.assertTrue(checkIfHeroExists(hero.getHeroName(), sAuthUser, sAuthPass), "Hero '" + hero.getHeroName() + "' doesn't exist!");
        Response response = editHeroApiCall(hero, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in editHero(" + hero.getHeroName() + ") Api Call! Response Body: " + sResponseBody);
    }

    /**
     * API Call: Edit Hero
     *
     * @param hero {Hero} Hero
     */
    public static void editHero(Hero hero) {
        editHero(hero, sAdminUser, sAdminPass);
    }


    // -----------------------
    // ----- DELETE HERO -----
    // -----------------------

    private static Response deleteHeroApiCall(String sHeroName, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createDeleteHeroApiCall(sHeroName);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                    .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().redirects().follow(false)
                    .delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in deleteHero(" + sHeroName + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    /**
     * API Call: Delete Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     */
    public static void deleteHero(String sHeroName, String sAuthUser, String sAuthPass) {
        log.debug("deleteHero(" + sHeroName + ")");
        Assert.assertTrue(checkIfHeroExists(sHeroName,sAuthUser, sAuthPass), "Hero '" + sHeroName + "' doesn't exist!");
        Response response = deleteHeroApiCall(sHeroName, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in deleteHero(" + sHeroName + ") Api Call! Response Body: " + sResponseBody);
        log.debug("Hero Deleted: " + !checkIfHeroExists(sHeroName, sAuthUser, sAuthPass));
    }

    /**
     * API Call: Delete Hero with specified Hero Name
     *
     * @param sHeroName {String} Hero Name
     */
    public static void deleteHero(String sHeroName) {
        deleteHero(sHeroName, sAdminUser, sAdminPass);
    }


    // ***************************************
    // ********** HEROES API ERRORS **********
    // ***************************************

    // --------------------------
    // ----- GET HERO ERROR -----
    //---------------------------

    /**
     * API Call: Get Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String getHeroErrorJsonFormat(String sHeroName, String sAuthUser, String sAuthPass) {
        Response response = getHeroApiCall(sHeroName, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Get Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @return {String} ApiError details in Json format
     */
    public static String getHeroErrorJsonFormat(String sHeroName) {
        return getHeroErrorJsonFormat(sHeroName, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Get Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError getHeroError(String sHeroName, String sAuthUser, String sAuthPass) {
        log.debug("getHeroError(" + sHeroName + ")");
        String json = getHeroErrorJsonFormat(sHeroName, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Get Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError getHeroError(String sHeroName) {
        return getHeroError(sHeroName, sAdminUser, sAdminPass);
    }


    // ---------------------------
    // ----- POST HERO ERROR -----
    //----------------------------

    /**
     * API Call: Post Hero Error
     *
     * @param hero {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String postHeroErrorJsonFormat(Hero hero, String sAuthUser, String sAuthPass) {
        Response response = postHeroApiCall(hero, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Post Hero Error
     *
     * @param hero {Hero} Hero
     * @return {String} ApiError details in Json format
     */
    public static String postHeroErrorJsonFormat(Hero hero) {
        return postHeroErrorJsonFormat(hero, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Post Hero Error
     *
     * @param hero {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError postHeroError(Hero hero, String sAuthUser, String sAuthPass) {
        log.debug("postHeroError(" + hero.getHeroName() + ")");
        String json = postHeroErrorJsonFormat(hero, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Post Hero Error
     *
     * @param hero {Hero} Hero
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError postHeroError(Hero hero) {
        return postHeroError(hero, sAdminUser, sAdminPass);
    }


    // ---------------------------
    // ----- EDIT HERO ERROR -----
    //----------------------------

    /**
     * API Call: Edit Hero Error
     *
     * @param hero {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String editHeroErrorJsonFormat(Hero hero, String sAuthUser, String sAuthPass) {
        Response response = editHeroApiCall(hero, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Edit Hero Error
     *
     * @param hero {Hero} Hero
     * @return {String} ApiError details in Json format
     */
    public static String editHeroErrorJsonFormat(Hero hero) {
        return editHeroErrorJsonFormat(hero, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Edit Hero Error
     *
     * @param hero {Hero} Hero
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError editHeroError(Hero hero, String sAuthUser, String sAuthPass) {
        log.debug("editHeroError(" + hero.getHeroName() + ")");
        String json = editHeroErrorJsonFormat(hero, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Edit Hero Error
     *
     * @param hero {Hero} Hero
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError editHeroError(Hero hero) {
        return editHeroError(hero, sAdminUser, sAdminPass);
    }


    // -----------------------------
    // ----- DELETE HERO ERROR -----
    //------------------------------

    /**
     * API Call: Delete Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {String} ApiError details in Json format
     */
    public static String deleteHeroErrorJsonFormat(String sHeroName, String sAuthUser, String sAuthPass) {
        Response response = deleteHeroApiCall(sHeroName, sAuthUser, sAuthPass);
        return response.getBody().asString();
    }

    /**
     * API Call: Delete Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @return {String} ApiError details in Json format
     */
    public static String deleteHeroErrorJsonFormat(String sHeroName) {
        return deleteHeroErrorJsonFormat(sHeroName, sAdminUser, sAdminPass);
    }

    /**
     * API Call: Delete Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @param sAuthUser {String} Authorization Username
     * @param sAuthPass {String} Authorization Password
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError deleteHeroError(String sHeroName, String sAuthUser, String sAuthPass) {
        log.debug("deleteHeroError(" + sHeroName + ")");
        String json = deleteHeroErrorJsonFormat(sHeroName, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        return gson.fromJson(json, ApiError.class);
    }

    /**
     * API Call: Delete Hero Error
     *
     * @param sHeroName {String} Hero Name
     * @return {ApiError} ApiError object with all error details
     */
    public static ApiError deleteHeroError(String sHeroName) {
        return deleteHeroError(sHeroName, sAdminUser, sAdminPass);
    }

}
