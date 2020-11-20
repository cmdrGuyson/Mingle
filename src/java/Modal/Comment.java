package Modal;

public class Comment {
    
    private String commentBody, owner, ownerProfileImage, userEmail;

    private int commentID, postID;
    
    public Comment(String commentBody, String userEmail, int postID) {
        this.commentBody = commentBody;
        this.userEmail = userEmail;
        this.postID = postID;
    }
    
    public Comment() {
        //NULL
    }
     
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getOwnerProfileImage() {
        return ownerProfileImage;
    }

    public void setOwnerProfileImage(String ownerProfileImage) {
        this.ownerProfileImage = ownerProfileImage;
    }
    
    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
}
