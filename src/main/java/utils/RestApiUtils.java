package utils;

import com.google.gson.Gson;
import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.User;
import org.testng.Assert;

public class RestApiUtils {

    private static final String ADMIN_USER = PropertiesUtils.getAdminUsername();
    private static final String ADMIN_PASS = PropertiesUtils.getAdminPassword();

    // Check if User Exists
    private static Response checkIfUserExistsApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = ApiCalls.createCheckIfUserExistsApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass).headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in checkIfUserExists(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static boolean checkIfUserExists(String sUsername, String sAuthUser, String sAuthPass) {
        LoggerUtils.log.debug("checkIfUserExists(" + sUsername + ")");
        Response response = checkIfUserExistsApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in checkIfUserExists(" + sUsername + ") Api Call! Status Code: " + status + ". Response Body: " + sResponseBody);
        if (!(sResponseBody.equals("true") || sResponseBody.equals("false"))) {
            Assert.fail("Cannot convert Response Body to boolean value! Response Body: " + sResponseBody);
        }
        return Boolean.parseBoolean(sResponseBody);
    }

    public static boolean checkIfUserExists(String sUsername) {
        return checkIfUserExists(sUsername, ADMIN_USER, ADMIN_PASS);
    }

    // Delete User
    private static Response deleteUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = ApiCalls.createDeleteUserApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass).headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in deleteUser(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static void deleteUser(String sUsername, String sAuthUser, String sAuthPass) {
        LoggerUtils.log.debug("deleteUser(" + sUsername + ")");
        Response response = deleteUserApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in deleteUser(" + sUsername + ") Api Call! Status Code: " + status + ". Response Body: " + sResponseBody);
        LoggerUtils.log.debug("User '" + sUsername + "' Deleted: " + !checkIfUserExists(sUsername, sAuthUser, sAuthPass));
    }

    public static void deleteUser(String sUsername) {
        deleteUser(sUsername, ADMIN_USER, ADMIN_PASS);
    }

    // Get User
    private static Response getUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = ApiCalls.createGetUserApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass).headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .when().get(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in getUser(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    private static String getUserJsonFormat(String sUsername, String sAuthUser, String sAuthPass) {
        Assert.assertTrue(checkIfUserExists(sUsername, sAuthUser, sAuthPass), "User '" + sUsername + "' doesn't exist!");
        Response response = getUserApiCall(sUsername, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asPrettyString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in getUser(" + sUsername + ") Api Call! Status Code: " + status + ". Response Body: " + sResponseBody);
        return sResponseBody;
    }

    public static String getUserJsonFormat(String sUsername) {
        return getUserJsonFormat(sUsername, ADMIN_USER, ADMIN_PASS);
    }

    public static User getUser(String sUsername, String sAuthUser, String sAuthPass) {
        LoggerUtils.log.debug("getUser(" + sUsername + ")");
        String json = getUserJsonFormat(sUsername, sAuthUser, sAuthPass);
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, User.class);
    }

    public static User getUser(String sUsername) {
        return getUser(sUsername, ADMIN_USER, ADMIN_PASS);
    }

    // Post User
    private static Response postUserApiCall(User user, String sAuthUser, String sAuthPass) {
        String sApiCall = ApiCalls.createPostUserApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user, User.class);
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass).headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                    .body(json)
                    .when().post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in postUser(" + user.getUsername() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static void postUser(User user, String sAuthUser, String sAuthPass) {
        LoggerUtils.log.debug("postUser(" + user.getUsername() + ")");
        Assert.assertFalse(checkIfUserExists(user.getUsername(), sAuthUser, sAuthPass), "User '" + user.getUsername() + "' already exists!");
        Response response = postUserApiCall(user, sAuthUser, sAuthPass);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in postUser(" + user.getUsername() + ") Api Call! Status Code: " + status + ". Response Body: " + sResponseBody);
        LoggerUtils.log.debug("User '" + user.getUsername() + "' Created: " + checkIfUserExists(user.getUsername(), sAuthUser, sAuthPass));
    }

    public static void postUser(User user) {
        postUser(user, ADMIN_USER, ADMIN_PASS);
    }

}
