package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandleLike {

    private final Connection connection;

    public HandleLike() {
        connection = Database_Connection.connectToDatabase();
    }

    public void likePost(Like like) {

        try {
            //Statemnt to be executed to register a like
            PreparedStatement statement = connection.prepareStatement("INSERT INTO _Like (email, postID) VALUES (?,?);");

            statement.setString(1, like.getEmail());
            statement.setInt(2, like.getPostID());

            statement.executeUpdate();

            //Statement to be executed to update the like count in the post
            PreparedStatement newStatement = connection.prepareStatement("UPDATE Post SET likeCount = likeCount + 1 WHERE postID = ?;");

            newStatement.setInt(1, like.getPostID());

            newStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void unlikePost(Like like) {

        try {

            //Statment to be executed to remove a like
            PreparedStatement statement = connection.prepareStatement("DELETE FROM _Like WHERE email = ? AND postID = ?;");

            statement.setString(1, like.getEmail());
            statement.setInt(2, like.getPostID());

            statement.executeUpdate();

            //Statement to be executed to update the like count in the post
            PreparedStatement newStatement = connection.prepareStatement("UPDATE Post SET likeCount = likeCount - 1 WHERE postID = ?;");

            newStatement.setInt(1, like.getPostID());

            newStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean isPostLiked(int postID, String email) {

        boolean postLiked = false;

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Like WHERE email = ? AND postID = ?;");

            statement.setString(1, email);
            statement.setInt(2, postID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                postLiked = true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return postLiked;
    }
}
