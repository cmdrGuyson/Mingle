package Modal;

import java.text.SimpleDateFormat;
import java.util.Date;

/* Profile */
public class User {

    public User() {
        //NULL
    }
    
    public User(String email, String firstName, String lastName, String bio, String profileImageURL, String coverImageURL, String createdAt) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profileImageURL = profileImageURL;
        this.coverImageURL = coverImageURL;
        this.createdAt = createdAt;
    }
    
    private String email, password, firstName, lastName, bio, profileImageURL, coverImageURL, type, status, createdAt;
    
    /* REGISTER */
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = "user";
        this.status = "Active";
        
        /* Create a date formatter to convert java date to SQL friendly date */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        /* Set date registered date to the present date after converting to simple date format */
        this.createdAt = formatter.format(new Date());
        
    }
    
    /* WHEN LOGGING IN */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getCoverImageURL() {
        return coverImageURL;
    }

    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
