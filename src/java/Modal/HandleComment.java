package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HandleComment {

    private final Connection connection;

    public HandleComment() {

        connection = Database_Connection.connectToDatabase();

    }

    public void createComment(Comment comment) {

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Comment (commentBody, postID, email) VALUES (?,?,?);");

            // insert data into fields of the database table.
            statement.setString(1, comment.getCommentBody());
            statement.setInt(2, comment.getPostID());
            statement.setString(3, comment.getUserEmail());

            statement.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void deleteComment(int id) {
        try {

            // Statement to be executed to delete a given comment
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Comment WHERE commentID = ?");

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Get all comments for a specific post
    public List<Comment> getAllComments(int postID) {
        
        List<Comment> comments = new ArrayList<>();

        try {
            
            //Sorted in desc order of postID to make the most recent comment come to the top
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Comment WHERE postID = ? ORDER BY commentID DESC;");
            statement.setInt(1, postID);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create a new comment object and add into list to be returned
                Comment comment = new Comment();
                comment.setCommentBody(results.getString("commentBody"));
                comment.setCommentID(results.getInt("commentID"));
                comment.setPostID(postID);
                comment.setUserEmail(results.getString("email"));
                

                PreparedStatement newStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?;");
                newStatement.setString(1, comment.getUserEmail());
                ResultSet newResults = newStatement.executeQuery();

                while (newResults.next()) {
                    comment.setOwnerProfileImage(newResults.getString("profileImageURL"));
                    comment.setOwner(newResults.getString("firstName") + " " + newResults.getString("lastName"));
                }

                comments.add(comment);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return comments;
    }
}
