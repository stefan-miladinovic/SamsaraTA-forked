package objects;

import java.util.Date;

public class DatabaseHero {

    private String hero_id;
    private String name;
    private String type;
    private Integer level;
    private Date created;
    private String fk_user_id;

    public String getHero_id() {
        return hero_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getLevel() {
        return level;
    }

    public Date getCreated() {
        return created;
    }

    public String getFk_user_id() {
        return fk_user_id;
    }

    public void setHero_id(String hero_id) {
        this.hero_id = hero_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setFk_user_id(String fk_user_id) {
        this.fk_user_id = fk_user_id;
    }

    @Override
    public String toString() {
        return "DatabaseHero{" +
                "hero_id='" + hero_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", created=" + created +
                ", fk_user_id='" + fk_user_id + '\'' +
                '}';
    }
}
