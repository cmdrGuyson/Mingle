package Modal;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Praveen
 */
public class Post {
    
    private int postID, likeCount, commentCount, groupID;
    private String postBody, postImageURL, createdAt,privacy,userEmail,ownerProfileImage, owner, status;
    private boolean isPostLiked;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public Post() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.createdAt = formatter.format(new Date());
        this.commentCount = 0;
        this.likeCount = 0;
    }
    
    //Used when editing a post
    public Post(int postID, String postBody) {
        this.postID = postID;
        this.postBody = postBody;
    }
    
    public Post(int postId, String postBody, String createdAt, String privacy, String userEmail) {
        this.postID = postId;
        this.postBody = postBody; 
        this.privacy = privacy;
        this.userEmail = userEmail;
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.createdAt = formatter.format(new Date());
    }

    public Post(String postBody, String postImageURL, String privacy) {
        this.postBody = postBody;
        this.postImageURL = postImageURL;
        this.privacy = privacy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postId) {
        this.postID = postId;
    }
    
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public String getOwnerProfileImage() {
        return ownerProfileImage;
    }

    public void setOwnerProfileImage(String ownerProfileImage) {
        this.ownerProfileImage = ownerProfileImage;
    }

    public boolean isIsPostLiked() {
        return isPostLiked;
    }

    public void setIsPostLiked(boolean isPostLiked) {
        this.isPostLiked = isPostLiked;
    }
    
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}