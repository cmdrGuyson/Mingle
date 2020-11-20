package Modal;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HandleUser {

    private final Connection connection;

    public HandleUser() {

        /*Make connection upon instantiation*/
        connection = Database_Connection.connectToDatabase();

    }

    /* User login */
    public String authenticateUser(User user) {

        try {

            /* Statement to be executed to get all users */
            PreparedStatement statement = connection.prepareStatement("SELECT email, password, status, type FROM user");
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                //From each result obtained a user object is created with the credentials, this is then cross referenced with user entered credentials (stored in user object recieved in arguments)
                // Once found type of user is returned
                User selectedUser = new User();
                selectedUser.setEmail(results.getString("email"));
                selectedUser.setPassword(results.getString("password"));
                selectedUser.setStatus(results.getString("status"));
                selectedUser.setType(results.getString("type"));

                // If the user exists on the database
                if (user.getEmail().equals(selectedUser.getEmail()) && user.getPassword().equals(selectedUser.getPassword())) {

                    //According to the type of user return the user type as string
                    if (selectedUser.getType().equals("admin")) {
                        return "admin";
                    } else if (selectedUser.getType().equals("user") && selectedUser.getStatus().equals("Active")) {
                        return "user";
                    } else if ((selectedUser.getType().equals("user") && selectedUser.getStatus().equals("Inactive"))) {
                        return "inactive-user";
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        // If user is not found return "invalid"
        return "invalid";
    }

    /* Method to register users */
    public void registerUser(User user) throws MySQLIntegrityConstraintViolationException {

        try {

            /* Statement to be executed to register specific user */
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (email, password, firstName, lastName, createdAt, type, status, profileImageURL, coverImageURL, bio)" + " VALUES (?,?,?,?,?,?,?,?,?,?);");

            /* customize statement according to recieved user object */
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCreatedAt());
            statement.setString(6, user.getType());
            statement.setString(7, user.getStatus());
            statement.setString(8, "https://moonvillageassociation.org/wp-content/uploads/2018/06/default-profile-picture1.jpg");
            statement.setString(9, "https://www.askideas.com/media/13/Colorful-Board-Facebook-Cover-Picture.jpg");
            statement.setString(10, "");

            statement.execute();

        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);

            /* This exception will be thrown if users with same username or email exists within the system. */
            throw e;
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public User getUserProfile(String email) {

        User user = null;
        try {

            /* Statement to be executed to get a list of users */
            PreparedStatement statement = connection.prepareStatement("SELECT email, type, firstName, lastName, createdAt, bio, profileImageURL, coverImageURL FROM user WHERE email = '" + email + "';");
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                //Create new user object for each user retrieved and add to list
                user = new User(results.getString("email"), results.getString("firstName"), results.getString("lastName"), results.getString("bio"), results.getString("profileImageURL"), results.getString("coverImageURL"), results.getString("createdAt"));
                user.setType(results.getString("type"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return user;
    }

    public void updateBio(User user) {
        try {

            /* Statement to be executed to change the bio of a user */
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET bio = ? WHERE email = ?;");

            statement.setString(1, user.getBio());
            statement.setString(2, user.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateProfileImage(User user) {

        try {

            /* Statement to be executed to change the profileImageURL of a specified user */
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET profileImageURL = ? WHERE email = ?;");

            statement.setString(1, user.getProfileImageURL());
            statement.setString(2, user.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCoverImage(User user) {

        try {

            /* Statement to be executed to change the coverImageURL of a specified user */
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET coverImageURL = ? WHERE email = ?;");

            statement.setString(1, user.getCoverImageURL());
            statement.setString(2, user.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public List<User> searchUser(String searchString) {

        List<User> users = new ArrayList<>();

        //searchString is broken into keywords by whitespaces.
        String[] keywords = searchString.trim().split("\\s+");

        //creating a REGEXP that can be used to query.
        String searchExpression = String.join("|", keywords);

        try {

            //Searching through firstName and find user
            PreparedStatement statementA = connection.prepareStatement("SELECT * FROM User WHERE firstName REGEXP ?;");
            statementA.setString(1, searchExpression);

            ResultSet resultsA = statementA.executeQuery();

            while (resultsA.next()) {
                User user = new User();
                user.setBio(resultsA.getString("bio"));
                user.setProfileImageURL(resultsA.getString("profileImageURL"));
                user.setFirstName(resultsA.getString("firstName"));
                user.setLastName(resultsA.getString("lastName"));
                user.setEmail(resultsA.getString("email"));
                users.add(user);
            }

            //Searching through lastName and find user
            PreparedStatement statementB = connection.prepareStatement("SELECT * FROM User WHERE lastName REGEXP ?;");
            statementB.setString(1, searchExpression);

            ResultSet resultsB = statementB.executeQuery();

            while (resultsB.next()) {
                User user = new User();
                user.setBio(resultsA.getString("bio"));
                user.setProfileImageURL(resultsA.getString("profileImageURL"));
                user.setFirstName(resultsA.getString("firstName"));
                user.setLastName(resultsA.getString("lastName"));
                user.setEmail(resultsA.getString("email"));
                users.add(user);
            }

            //Removing duplicates from list by comparing emails and creating a tree set
            Set<String> emailSet = new HashSet<>();
            List<User> unique = users.stream()
                    .filter(e -> emailSet.add(e.getEmail()))
                    .collect(Collectors.toList());

            users = unique;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return users;
    }

    //Adding a new friend
    public void addFriend(String email, String r_email) {
        //email is friend request sender's email and r_email is recivers email

        try {

            //Statement to add a friend request
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Friend (friendEmail_A, friendEmail_B, status) VALUES (?,?,?)");

            statement.setString(1, email);
            statement.setString(2, r_email);
            statement.setString(3, "Pending");

            statement.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Accept friend request
    public void acceptFriendRequest(String email, String s_email) {
        //email is accepters email, s_email is request sender's email

        try {

            //Statement to add requester as a friend
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Friend (friendEmail_A, friendEmail_B, status) VALUES (?,?,?)");

            statement.setString(1, email);
            statement.setString(2, s_email);
            statement.setString(3, "Accepted");

            statement.execute();

            //Statement to accept the friend request (update senders friend list)
            PreparedStatement statementB = connection.prepareStatement("UPDATE Friend SET status = ? WHERE friendEmail_A = ? AND friendEmail_B = ?;");
            statementB.setString(1, "Accepted");
            statementB.setString(2, s_email);
            statementB.setString(3, email);

            statementB.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Reject friend request
    public void rejectFriendRequest(String email, String s_email) {
        //email is rejectors email, s_email is request sender's email

        try {

            //Statement to reject a friend request
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Friend WHERE friendEmail_A = ? AND friendEmail_B = ?");

            statement.setString(1, s_email);
            statement.setString(2, email);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    //Remove a friend
    public void removeFriend(String email_a, String email_b) {
        try {

            //Statement to remove friend from user A's friend list
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Friend WHERE friendEmail_A = ? AND friendEmail_B = ?");

            statement.setString(1, email_a);
            statement.setString(2, email_b);

            statement.executeUpdate();

            //Statement to remove friend from user B's friend list
            PreparedStatement statementB = connection.prepareStatement("DELETE FROM Friend WHERE friendEmail_A = ? AND friendEmail_B = ?");

            statementB.setString(1, email_b);
            statementB.setString(2, email_a);

            statementB.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Check if someone is your friend
    public String getFriendStatus(String email, String u_email) {

        String status = "None";

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friend WHERE friendEmail_A = ? AND friendEmail_B = ?");

            statement.setString(1, email);
            statement.setString(2, u_email);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                status = results.getString("status");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return status;
    }

    //Check if someone had sent a friend request
    public String friendRequestSent(String email, String u_email) {
        String data = "not_sent";

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friend WHERE friendEmail_A = ? AND friendEmail_B = ? AND status = 'Pending'");

            statement.setString(1, u_email);
            statement.setString(2, email);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                data = "sent";
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return data;
    }

    //Get all friends of a user
    public List<User> getAllFriends(String email) {

        List<User> users = new ArrayList<>();

        try {

            //Get all emails of your friends
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friend WHERE friendEmail_A = ? AND status = 'Accepted'");

            statement.setString(1, email);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                //Using email find user information, create user objects and add to array list of users
                String friendEmail = results.getString("friendEmail_B");

                PreparedStatement statementB = connection.prepareStatement("SELECT * FROM User WHERE email = ?;");
                statementB.setString(1, friendEmail);

                ResultSet resultsB = statementB.executeQuery();

                while (resultsB.next()) {
                    User user = new User();
                    user.setBio(resultsB.getString("bio"));
                    user.setProfileImageURL(resultsB.getString("profileImageURL"));
                    user.setFirstName(resultsB.getString("firstName"));
                    user.setLastName(resultsB.getString("lastName"));
                    user.setEmail(resultsB.getString("email"));
                    users.add(user);
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        //return created array list of users (friends)
        return users;
        
    }
    
    //Get all friend requests recived by a user
    public List<User> getAllFriendRequests(String email) {

        List<User> users = new ArrayList<>();

        try {

            //Get all emails of people who have sent you friend requests
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friend WHERE friendEmail_B = ? AND status = 'Pending'");

            statement.setString(1, email);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                //Using email find user information, create user objects and add to array list of users
                String friendEmail = results.getString("friendEmail_A");

                PreparedStatement statementB = connection.prepareStatement("SELECT * FROM User WHERE email = ?;");
                statementB.setString(1, friendEmail);

                ResultSet resultsB = statementB.executeQuery();

                while (resultsB.next()) {
                    User user = new User();
                    user.setBio(resultsB.getString("bio"));
                    user.setProfileImageURL(resultsB.getString("profileImageURL"));
                    user.setFirstName(resultsB.getString("firstName"));
                    user.setLastName(resultsB.getString("lastName"));
                    user.setEmail(resultsB.getString("email"));
                    users.add(user);
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return users;
        
    }
}
