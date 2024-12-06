package utils;

import objects.DatabaseHero;
import objects.DatabaseUser;
import objects.Hero;
import objects.User;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

public class DatabaseUtils {

    public static final String DATA_SOURCE_URL = PropertiesUtils.getDataSourceUrl();
    public static final String ROOT_USERNAME = PropertiesUtils.getRootUsername();
    public static final String ROOT_PASSWORD = PropertiesUtils.getRootPassword();
    public static final String DATABASE_DRIVER = PropertiesUtils.getDatabaseDriver();

    // For older versions
    // com.mysql.jdbc.Driver

    // For newer versions
    // com.mysql.cj.jdbc.Driver

    private static Connection openConnection() {
        Connection connection = null;
        try {
            // Class.forName(DATABASE_DRIVER);
            // DbUtils.loadDriver(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
        } catch (SQLException e) {
            Assert.fail("Cannot open connection to Database. Message: " + e.getMessage());
        }
        return connection;
    }

    private static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                if(!connection.isClosed()) {
                    DbUtils.close(connection);
                }
            } catch (SQLException e) {
                Assert.fail("Cannot close connection to Database. Message: " + e.getMessage());
            }
        }
    }

    // SELECT user_id FROM users WHERE username = 'dedoje'
    public static String getUserID(String sUsername) {
        LoggerUtils.log.trace("getUserID(" + sUsername + ")");
        String sqlQuery = "SELECT user_id FROM users WHERE username = '" + sUsername + "'";
        Connection connection = openConnection();
        QueryRunner run = new QueryRunner();
        ScalarHandler<String> handler = new ScalarHandler<>();
        //ResultSetHandler<String> handler = new ScalarHandler<>();
        String result = null;
        try {
            result = run.query(connection, sqlQuery, handler);
        } catch (SQLException e) {
            Assert.fail("SQL Query failed. Message: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public static String getUsername(String sUserID) {
        LoggerUtils.log.trace("getUsername(" + sUserID + ")");
        String sqlQuery = "SELECT username FROM users WHERE user_id = ?";
        Connection connection = openConnection();
        QueryRunner run = new QueryRunner();
        ScalarHandler<String> handler = new ScalarHandler<>();
        //ResultSetHandler<String> handler = new ScalarHandler<>();
        String result = null;
        try {
            result = run.query(connection, sqlQuery, handler, sUserID);
        } catch (SQLException e) {
            Assert.fail("SQL Query failed. Message: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public static List<String> getAllUsernames() {
        LoggerUtils.log.trace("getAllUsernames()");
        String sqlQuery = "SELECT username FROM users";
        Connection connection = openConnection();
        QueryRunner run = new QueryRunner();
        ColumnListHandler<String> handler = new ColumnListHandler<>();
        //ResultSetHandler<List<String>> handler = new ColumnListHandler<>();
        List<String> result = null;
        try {
            result = run.query(connection, sqlQuery, handler);
        } catch (SQLException e) {
            Assert.fail("SQL Query failed. Message: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public static DatabaseUser getDatabaseUser(String sUsername) {
        LoggerUtils.log.trace("getDatabaseUser(" + sUsername + ")");
        String sqlQuery = "SELECT * FROM users WHERE username = ?";
        Connection connection = openConnection();
        QueryRunner run = new QueryRunner();
        BeanHandler<DatabaseUser> handler = new BeanHandler<>(DatabaseUser.class);
        //ResultSetHandler<DatabaseUser> handler = new BeanHandler<>(DatabaseUser.class);
        DatabaseUser result = null;
        try {
            result = run.query(connection, sqlQuery, handler, sUsername);
        } catch (SQLException e) {
            Assert.fail("SQL Query failed. Message: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    public static User getUser(String sUsername) {
        LoggerUtils.log.trace("getUser(" + sUsername + ")");
        DatabaseUser databaseUser = getDatabaseUser(sUsername);
        User user = assembleUser(databaseUser);
        return user;
    }

    public static List<DatabaseHero> getHeroesForUser(String sUserID) {
        LoggerUtils.log.trace("getHeroesForUser(" + sUserID + ")");
        String sqlQuery = "SELECT * FROM heroes WHERE fk_user_id = ?";
        Connection connection = openConnection();
        QueryRunner run = new QueryRunner();
        BeanListHandler<DatabaseHero> handler = new BeanListHandler<>(DatabaseHero.class);
        //ResultSetHandler<List<DatabaseHero>> handler = new BeanListHandler<>(DatabaseHero.class);
        List<DatabaseHero> result = null;
        try {
            result = run.query(connection, sqlQuery, handler, sUserID);
        } catch (SQLException e) {
            Assert.fail("SQL Query failed. Message: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    private static Hero assembleHero (DatabaseHero databaseHero) {
        String sHeroName = databaseHero.getName();
        String sHeroClass = databaseHero.getType();
        int iHeroLevel = databaseHero.getLevel();
        Date createdAt = databaseHero.getCreated();
        String sUsername = getUsername(databaseHero.getFk_user_id());
        Hero hero = Hero.createHero(sHeroName, sHeroClass, iHeroLevel, sUsername, createdAt);
        return hero;
    }

    private static User assembleUser (DatabaseUser databaseUser) {
        String sUsername = databaseUser.getUsername();
        String sPassword = databaseUser.getPassword();
        String sEmail = databaseUser.getEmail();
        String sFirstName = databaseUser.getFirst_name();
        String sLastName = databaseUser.getLast_name();
        String sAbout = databaseUser.getAbout();
        String sSecretQuestion = databaseUser.getSecret_question();
        String sSecretAnswer = databaseUser.getSecret_answer();
        Date dCreatedAt = databaseUser.getCreated();

        List<DatabaseHero> databaseHeroes = getHeroesForUser(databaseUser.getUser_id());
        List<Hero> heroes = new ArrayList<>();
        for (DatabaseHero databaseHero : databaseHeroes) {
            Hero hero = assembleHero(databaseHero);
            heroes.add(hero);
        }

        User user = User.createUser(sUsername, sPassword, sEmail, sFirstName, sLastName, sAbout, sSecretQuestion, sSecretAnswer, dCreatedAt, heroes);
        return user;
    }
}
