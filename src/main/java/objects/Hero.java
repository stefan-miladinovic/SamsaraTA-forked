package objects;

import com.google.gson.annotations.SerializedName;
import utils.DateTimeUtils;

import java.util.Date;
import java.util.Objects;

public class Hero {

    @SerializedName("name")
    private String heroName;

    @SerializedName("type")
    private String heroClass;

    @SerializedName("level")
    private Integer heroLevel;

    private String username;

    private Long createdAt;

    private Hero(String heroName, String heroClass, Integer heroLevel, String username, Date createdAt) {
        this.heroName = heroName;
        this.heroClass = heroClass;
        this.heroLevel = heroLevel;
        this.username = username;
        setCreatedAt(createdAt);
    }

    private Hero(String heroName, String heroClass, Integer heroLevel, String username) {
        this.heroName = heroName;
        this.heroClass = heroClass;
        this.heroLevel = heroLevel;
        this.username = username;
        setCreatedAt(null);
    }

    private Hero(String heroName, String heroClass, Integer heroLevel) {
        this.heroName = heroName;
        this.heroClass = heroClass;
        this.heroLevel = heroLevel;
        setUsername(null);
        setCreatedAt(null);
    }

    // TODO: Create Constructor with random HeroClass and HeroLevel

    public String getHeroName() {
        return heroName;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public int getHeroLevel() {
        if (heroLevel == null) {
            return -1;
        }
        return heroLevel;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return DateTimeUtils.getDateTime(this.createdAt);
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public void setHeroLevel(int heroLevel) {
        this.heroLevel = heroLevel;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static Hero createNewUniqueHero(String heroName, String heroClass, int heroLevel) {
        String sHeroName = heroName + DateTimeUtils.getDateTimeStamp();
        return new Hero(sHeroName, heroClass, heroLevel);
    }

    public static Hero createNewUniqueHero(User user, String heroName, String heroClass, int heroLevel) {
        String sHeroName = heroName + DateTimeUtils.getDateTimeStamp();
        String sUsername = user.getUsername();
        return new Hero(sHeroName, heroClass, heroLevel, sUsername);
    }

    @Override
    public String toString() {
        return "Hero {"
                + "Name: " + getHeroName() + ", "
                + "Class: " + getHeroClass() + ", "
                + "Level: " + getHeroLevel() + ", "
                + "Username: " + getUsername() + ", "
                + "Created at: " + getCreatedAt() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(getHeroName(), hero.getHeroName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHeroName());
    }
}
