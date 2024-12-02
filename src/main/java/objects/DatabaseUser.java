package objects;

import java.util.Date;

public class DatabaseUser {

    private String user_id;
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String about;
    private Boolean enabled;
    private String secret_question;
    private String secret_answer;
    private Date created;

    public String getUser_id() {
        return user_id;
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAbout() {
        return about;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getSecret_question() {
        return secret_question;
    }

    public String getSecret_answer() {
        return secret_answer;
    }

    public Date getCreated() {
        return created;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setSecret_question(String secret_question) {
        this.secret_question = secret_question;
    }

    public void setSecret_answer(String secret_answer) {
        this.secret_answer = secret_answer;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "DatabaseUser{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", about='" + about + '\'' +
                ", enabled=" + enabled +
                ", secret_question='" + secret_question + '\'' +
                ", secret_answer='" + secret_answer + '\'' +
                ", created=" + created +
                '}';
    }
}
