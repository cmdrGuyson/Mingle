package Modal;

public class Like {
    
    private int likeID, postID;
    private String email;
    
    public Like(int postID, String email) {
        this.postID = postID;
        this.email = email;
    }
    
    public int getLikeID() {
        return likeID;
    }

    public void setLikeID(int likeID) {
        this.likeID = likeID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
