package Modal;

import java.util.ArrayList;

public class Group {

    private int groupID, pendingPosts;
    private ArrayList<String> groupMemberList = new ArrayList<>();
    private ArrayList<Post> postList = new ArrayList<>();
    private String groupName, description, groupImageURL, status, admin;

    public Group(String groupName, String description, String admin) {

        this.groupName = groupName;
        this.description = description;
        this.admin = admin;
        this.groupMemberList.add(admin);

        // Default group image
        this.groupImageURL = "https://www.tibs.org.tw/images/default.jpg";

        // This wil be changed later when admin approval is implemented
        this.status = "Inactive";

    }

    public Group() {
        //NULL
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public ArrayList<String> getGroupMemberList() {
        return groupMemberList;
    }
    
    public int getPendingPosts() {
        return pendingPosts;
    }

    public void setPendingPosts(int pendingPosts) {
        this.pendingPosts = pendingPosts;
    }

    public void setGroupMemberList(ArrayList<String> groupMemberList) {
        this.groupMemberList = groupMemberList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupImageURL() {
        return groupImageURL;
    }

    public void setGroupImageURL(String groupImageURL) {
        this.groupImageURL = groupImageURL;
    }

    public void addGroupMember(String member) {
        this.groupMemberList.add(member);
    }

    public void removeGroupMember(String member) {
        this.groupMemberList.remove(member);
    }

    public void addPost(Post post) {
        this.postList.add(post);
    }

    public void removePost(Post post) {
        this.postList.remove(post);
    }

}
