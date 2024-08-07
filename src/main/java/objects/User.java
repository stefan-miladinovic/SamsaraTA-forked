package objects;

// POJO - Plane Old Java Object

import com.github.javafaker.Faker;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String about;
    private String secretQuestion;
    private String secretAnswer;
    private Long createdAt;
    private List<Hero> heroes;

    private User(String username, String password, String email, String firstName, String lastName, String about, String secretQuestion, String secretAnswer, Date createdAt, List<Hero> heroes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        setCreatedAt(createdAt);
        this.heroes = heroes;
    }

    private User(String username, String password, String email, String firstName, String lastName, String about, String secretQuestion, String secretAnswer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        setCreatedAt(null);
        setHeroes(null);
    }

    private User(String username) {
        String sPassword = PropertiesUtils.getDefaultPassword();
        String sEmail = username + "@mail.com";
        String sFirstName = createRandomFirstName();
        String sLastName = createRandomLastName();
        String sAbout = "About Me Text!";
        String sSecretQuestion = PropertiesUtils.getDefaultSecretQuestion();
        String sSecretAnswer = PropertiesUtils.getDefaultSecretAnswer();

        this.setUsername(username);
        this.setPassword(sPassword);
        this.setEmail(sEmail);
        this.setFirstName(sFirstName);
        this.setLastName(sLastName);
        this.setAbout(sAbout);
        this.setSecretQuestion(sSecretQuestion);
        this.setSecretAnswer(sSecretAnswer);
        this.setCreatedAt(null);
        this.setHeroes(null);
    }

    private static String createRandomFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    private static String createRandomLastName() {
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    private static String createRandomAboutMeText() {
        Faker faker = new Faker();
        return faker.yoda().quote();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    public String getAbout() {
        return about;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public Date getCreatedAt() {
        if (this.createdAt == null) {
            return null;
        } else {
            return DateTimeUtils.getDateTime(this.createdAt);
        }
    }

    public int getHeroCount() {
        if (heroes == null) {
            return 0;
        } else {
            return heroes.size();
        }
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Hero getHero(String sHeroName) {
        if(heroes != null) {
            for(Hero hero : heroes) {
                if(hero.getHeroName().equals(sHeroName)) {
                    return hero;
                }
            }
        }
        Assert.fail("User '" + getUsername() + "' doesn't have Hero '" + sHeroName + "!");
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Date date) {
        if (date == null) {
            this.createdAt = null;
        } else {
            this.createdAt = date.getTime();
        }
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public void addHero(Hero hero) {
        if(heroes == null) {
            heroes = new ArrayList<>();
        }
        if(!heroes.contains(hero)) {
            hero.setUsername(getUsername());
            heroes.add(hero);
        } else {
            Assert.fail(("User '" + getUsername() + "' cannot have two heroes with the same name '" + hero.getHeroName() + "!"));
        }
    }

    public void removeHero(Hero hero) {
        if(heroes == null) {
            Assert.fail(("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "!"));
        }
        if(heroes.contains(hero)) {
            heroes.remove(hero);
        } else {
            Assert.fail(("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "!"));
        }
    }

    public void updateHero(Hero hero) {
        if(heroes == null) {
            Assert.fail(("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "!"));
        }
        if(heroes.contains(hero)) {
            int index = heroes.indexOf(hero);
            hero.setUsername(getUsername());
            heroes.set(index, hero);
        } else {
            Assert.fail(("User '" + getUsername() + "' doesn't have hero with the name '" + hero.getHeroName() + "!"));
        }
    }

    public static User createNewUniqueUser(String username) {
        String sUsername = username.toLowerCase() + DateTimeUtils.getDateTimeStamp();
        if (sUsername.length() > 35) {
            Assert.fail("Username '" + sUsername + "' has more than 35 characters!");
        }
        return new User(sUsername);
    }

    @Override
    public String toString() {
        return "User {"
                + "Username: " + getUsername() + ", "
                + "Password: " + getPassword() + ", "
                + "Email: " + getEmail() + ", "
                + "Name: " + getDisplayName() + ", "
                + "About: " + getAbout() + ", "
                + "Secret Question: " + getSecretQuestion() + ", "
                + "Secret Answer: " + getSecretAnswer() + ", "
                + "Created at: " + getCreatedAt() + ", "
                + "Hero Count: " + getHeroCount() + ", "
                + "Heroes: " + getHeroes() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername());
    }
}
