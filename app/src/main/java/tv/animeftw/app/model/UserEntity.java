package tv.animeftw.app.model;

/**
 * Created by darshanz on 4/19/16.
 */
public class UserEntity {
    private String id;
    private String username;
    private String lastactivity;
    private String email;
    private String views;
    private String firstname;
    private String lastname;
    private String gender;
    private String birthday;
    private String country;
    private String avatar;
    private String personalmsg;
    private String advancemember;
    private String timezone;
    private int newmessages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastactivity() {
        return lastactivity;
    }

    public void setLastactivity(String lastactivity) {
        this.lastactivity = lastactivity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPersonalmsg() {
        return personalmsg;
    }

    public void setPersonalmsg(String personalmsg) {
        this.personalmsg = personalmsg;
    }

    public String getAdvancemember() {
        return advancemember;
    }

    public void setAdvancemember(String advancemember) {
        this.advancemember = advancemember;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getNewmessages() {
        return newmessages;
    }

    public void setNewmessages(int newmessages) {
        this.newmessages = newmessages;
    }
}
