package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Praveen
 */
public class HandlePost {

    private final Connection connection;

    public HandlePost() {

        connection = Database_Connection.connectToDatabase();

    }

    public void createPost(Post post) {

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Post (postBody,postImageURL,privacy,email,createdAt, likeCount, commentCount) VALUES (?,?,?,?,?,?,?)");

            // insert data into fields of the database table.
            statement.setString(1, post.getPostBody());
            statement.setString(2, post.getPostImageURL());
            statement.setString(3, post.getPrivacy());
            statement.setString(4, post.getUserEmail());
            statement.setString(5, post.getCreatedAt());
            statement.setInt(6, post.getLikeCount());
            statement.setInt(7, post.getCommentCount());

            statement.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deletePost(int id) {
        try {
            //Before deleting the post all likes and comments of the post should be deleted
            PreparedStatement likesStatement = connection.prepareStatement("DELETE FROM _Like WHERE postID = ?");
            likesStatement.setInt(1, id);
            likesStatement.executeUpdate();

            PreparedStatement delStatement = connection.prepareStatement("DELETE FROM Comment WHERE postID = ?");
            delStatement.setInt(1, id);
            delStatement.executeUpdate();

            // Statement to be executed to delete a given post
            PreparedStatement statement = connection.prepareStatement("delete from post where postID=?");

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Post> getAllPosts() {

        List<Post> posts = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM post ORDER BY postID DESC;");
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create a new post object and add into list to be returned
                Post post = new Post();
                post.setPostID(results.getInt("postID"));
                post.setPostBody(results.getString("postBody"));
                post.setPostImageURL(results.getString("postImageURL"));
                post.setCreatedAt(results.getString("createdAt"));
                post.setUserEmail(results.getString("email"));

                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, post.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    post.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    post.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

                posts.add(post);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return posts;
    }

    public Post getPost(int postID) {

        Post post = new Post();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Post WHERE postID = ?;");
            statement.setInt(1, postID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // Get information about a single post using given postID and create a post object to be returned
                post.setPostID(results.getInt("postID"));
                post.setPostBody(results.getString("postBody"));
                post.setPostImageURL(results.getString("postImageURL"));
                post.setCreatedAt(results.getString("createdAt"));
                post.setUserEmail(results.getString("email"));
                post.setLikeCount(results.getInt("likeCount"));

                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, post.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    post.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    post.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return post;
    }

    //Method to get posts of a specified user
    public List<Post> getUsersPosts(String email, String friendStatus) {

        List<Post> posts = new ArrayList<>();

        try {
            PreparedStatement statement;

            //If selected user is the logged in user or selected user is a friend of the logged in user retrieve all posts that aren't group posts
            if (friendStatus.equals("Accepted") || friendStatus.equals("Self")) {
                statement = connection.prepareStatement("SELECT * FROM post WHERE email = ? AND groupID IS NULL ORDER BY postID DESC;");
            } else {
                // If not select all "Public" posts that aren't group posts    
                statement = connection.prepareStatement("SELECT * FROM post WHERE email = ? AND privacy='Public' AND groupID IS NULL ORDER BY postID DESC;");
            }

            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create a new post object and add into list to be returned
                Post post = new Post();
                post.setPostID(results.getInt("postID"));
                post.setPostBody(results.getString("postBody"));
                post.setPostImageURL(results.getString("postImageURL"));
                post.setCreatedAt(results.getString("createdAt"));
                post.setUserEmail(results.getString("email"));
                post.setPrivacy(results.getString("privacy"));

                //Using another statement get details of user to be added to post object
                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, post.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    //Add profile image and name of user to post object
                    post.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    post.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

                //Add post object to the posts list
                posts.add(post);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        //Return the list of posts
        return posts;
    }

    public void editPost(Post post) {
        try {

            //Statement to edit a post
            PreparedStatement statement = connection.prepareStatement("UPDATE Post SET postBody = ? WHERE postID = ?;");

            //Add new post body
            statement.setString(1, post.getPostBody());

            //Set the postID
            statement.setInt(2, post.getPostID());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Creating a group specific post
    public void createGroupPost(Post post) {

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Post (postBody,postImageURL,email,createdAt, likeCount, commentCount, status, groupID) VALUES (?,?,?,?,?,?,?,?)");

            // insert data into fields of the database table.
            statement.setString(1, post.getPostBody());
            statement.setString(2, post.getPostImageURL());
            statement.setString(3, post.getUserEmail());
            statement.setString(4, post.getCreatedAt());
            statement.setInt(5, post.getLikeCount());
            statement.setInt(6, post.getCommentCount());
            statement.setString(7, "Pending");
            statement.setInt(8, post.getGroupID());

            statement.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Method for group admin to accept pending group posts
    public void acceptPost(int postID) {
        try {

            PreparedStatement statement = connection.prepareStatement("UPDATE Post SET status = 'Active' WHERE postID = ?");
            statement.setInt(1, postID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Get all posts relavent to a certain group
    public List<Post> getAllGroupPosts(int groupID) {

        List<Post> posts = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM post WHERE groupID = ? ORDER BY postID DESC;");
            statement.setInt(1, groupID);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create a new post object and add into list to be returned
                Post post = new Post();
                post.setPostID(results.getInt("postID"));
                post.setPostBody(results.getString("postBody"));
                post.setPostImageURL(results.getString("postImageURL"));
                post.setCreatedAt(results.getString("createdAt"));
                post.setUserEmail(results.getString("email"));
                post.setGroupID(results.getInt("groupID"));
                post.setStatus(results.getString("status"));

                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, post.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    post.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    post.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

                posts.add(post);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return posts;
    }

    //Get all posts to view on a specific user's home page
    public List<Post> getAllPosts(String email) {

        List<Post> posts = new ArrayList<>();

        try {

            //Statement to retrieve all posts of user's friends and user
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Post WHERE email IN (SELECT friendEmail_B from Friend WHERE friendEmail_A = ? AND status = 'Accepted') OR email = ? AND groupID IS NULL ORDER BY postID DESC;");
            statement.setString(1, email);
            statement.setString(2, email);

            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create a new post object and add into list to be returned
                Post post = new Post();
                post.setPostID(results.getInt("postID"));
                post.setPostBody(results.getString("postBody"));
                post.setPostImageURL(results.getString("postImageURL"));
                post.setCreatedAt(results.getString("createdAt"));
                post.setUserEmail(results.getString("email"));
                post.setGroupID(results.getInt("groupID"));
                post.setPrivacy(results.getString("privacy"));

                //Statement to get details about post owner
                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, post.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    post.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    post.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

                if (post.getGroupID() == 0) {
                    posts.add(post);
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return posts;
    }

}
