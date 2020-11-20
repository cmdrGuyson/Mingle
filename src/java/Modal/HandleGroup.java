package Modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HandleGroup {

    private final Connection connection;

    public HandleGroup() {
        connection = Database_Connection.connectToDatabase();
    }

    public void createGroup(Group group) {

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO _Group (groupName, groupDescription, groupImageURL, status) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            // insert data into fields of the database table.
            statement.setString(1, group.getGroupName());
            statement.setString(2, group.getDescription());
            statement.setString(3, group.getGroupImageURL());
            statement.setString(4, group.getStatus());

            statement.executeUpdate();

            // Identify the auto generated groupID
            ResultSet results = statement.getGeneratedKeys();
            results.next();

            int groupID = results.getInt(1);
            System.out.println(groupID);

            // Link new user to group
            PreparedStatement statementB = connection.prepareStatement("INSERT INTO Group_User (groupID, email) VALUES (?, ?)");
            statementB.setInt(1, groupID);
            statementB.setString(2, group.getGroupMemberList().get(0));

            statementB.executeUpdate();

            // Link admin to group
            PreparedStatement statementC = connection.prepareStatement("INSERT INTO Group_Admin (groupID, email) VALUES (?, ?)");
            statementC.setInt(1, groupID);
            statementC.setString(2, group.getAdmin());

            statementC.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Get all groups of specified status
    public List<Group> getAllGroups(String status) {

        List<Group> groups = new ArrayList<>();

        try {

            // Statement to be executed to get all groups in the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Group WHERE status = ? ORDER BY groupID DESC;");
            statement.setString(1, status);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create group objects and add to list
                Group group = new Group();
                group.setGroupID(results.getInt("groupID"));
                group.setGroupName(results.getString("groupName"));
                group.setDescription(results.getString("groupDescription"));
                group.setGroupImageURL(results.getString("groupImageURL"));
                group.setStatus(results.getString("status"));

                groups.add(group);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        // Return the list
        return groups;

    }

    // Get information on a single group
    public Group getSingleGroup(int groupID) {

        Group group = new Group();

        try {

            // Prepare the statement and set the groupID
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Group WHERE groupID = ?");
            statement.setInt(1, groupID);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                // Create new group from the results
                group.setGroupID(results.getInt("groupID"));
                group.setGroupName(results.getString("groupName"));
                group.setDescription(results.getString("groupDescription"));
                group.setGroupImageURL(results.getString("groupImageURL"));
                group.setStatus(results.getString("status"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        //return group object
        return group;
    }

    // Check if given user is an admin of a given group
    public boolean isGroupAdmin(int groupID, String email) {

        boolean isGroupAdmin = false;

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Group_Admin WHERE groupID = ?");
            statement.setInt(1, groupID);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // If the foreign key email of a group is the same as given email then return true value
                if (results.getString("email").equals(email)) {
                    isGroupAdmin = true;
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return isGroupAdmin;

    }

    public void changeGroupDescription(Group group) {
        try {

            PreparedStatement statement = connection.prepareStatement("UPDATE _Group SET groupDescription = ? WHERE groupID = ?;");

            // Update a group's description according to user entered description
            statement.setString(1, group.getDescription());
            statement.setInt(2, group.getGroupID());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changeGroupImageURL(Group group) {
        try {

            PreparedStatement statement = connection.prepareStatement("UPDATE _Group SET groupImageURL = ? WHERE groupID = ?;");

            System.out.println(group.getGroupID());

            // Update a group's image URL according to uploaded image URL
            statement.setString(1, group.getGroupImageURL());
            statement.setInt(2, group.getGroupID());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Group> getMyGroups(String email) {

        List<Group> groups = new ArrayList<>();

        try {

            // Statement to be executed to get all groups that a user is part of
            PreparedStatement statement = connection.prepareStatement("SELECT g.groupID, g.groupName, g.groupDescription, g.groupImageURL, g.status, g_a.email FROM _group g JOIN group_user g_a ON g.groupID = g_a.groupID WHERE g_a.email LIKE ?;");
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create group objects and add to list
                Group group = new Group();
                group.setGroupID(results.getInt("groupID"));
                group.setGroupName(results.getString("groupName"));
                group.setDescription(results.getString("groupDescription"));
                group.setGroupImageURL(results.getString("groupImageURL"));
                group.setStatus(results.getString("status"));

                groups.add(group);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        // Return the list
        return groups;

    }
    
    //Get all groups of all statuses
    public List<Group> getAllGroups() {

        List<Group> groups = new ArrayList<>();

        try {

            // Statement to be executed to get all groups in the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Group ORDER BY groupID DESC;");
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create group objects and add to list
                Group group = new Group();
                group.setGroupID(results.getInt("groupID"));
                group.setGroupName(results.getString("groupName"));
                group.setDescription(results.getString("groupDescription"));
                group.setGroupImageURL(results.getString("groupImageURL"));
                group.setStatus(results.getString("status"));

                groups.add(group);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        // Return the list
        return groups;

    }

    /*Activate or Deactivate group*/
    public void manageGroup(int groupID) {

        try {
            //Statement to be executed to find the group to change status of
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Group WHERE groupID = ?;");
            statement.setInt(1, groupID);

            ResultSet results = statement.executeQuery();

            String status = null;

            while (results.next()) {
                //Find out whether group is already active or inactive and decide the toggle state
                status = (results.getString("status").equals("Active")) ? "Inactive" : "Active";
            }

            //Set the new status of the group
            PreparedStatement newStatement = connection.prepareStatement("UPDATE _Group SET status = ? WHERE groupID = ?;");
            newStatement.setString(1, status);
            newStatement.setInt(2, groupID);

            newStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    public int getGroupNotifications(String email) {
        
        int count = 0;
        
        try{
            //Statement to get number of total posts to be accepted/rejected in user's groups
            PreparedStatement statement = connection.prepareStatement("select * from post p join (select groupID from Group_Admin where email = ?) s on s.groupID = p.groupId and status = 'Pending';");
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();
            
            while(results.next()){
                count++;
            }
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return count;
    }
    
    private int getGroupNotifications(String email, int groupID) {
        
        int count = 0;
        
        try{
            //Statement to get number of total posts to be accepted/rejected in user's groups
            PreparedStatement statement = connection.prepareStatement("select * from post p join (select groupID from Group_Admin where email = ?) s on s.groupID = p.groupId and status = 'Pending';");
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();
            
            while(results.next()){
                count++;
            }
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return count;
    }
    
    //Get all groups of specified status
    public List<Group> getAllGroups(String status, String email) {

        List<Group> groups = new ArrayList<>();

        try {

            // Statement to be executed to get all groups in the database
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM _Group WHERE status = ? ORDER BY groupID DESC;");
            statement.setString(1, status);
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                // For all results create group objects and add to list
                Group group = new Group();
                group.setGroupID(results.getInt("groupID"));
                group.setGroupName(results.getString("groupName"));
                group.setDescription(results.getString("groupDescription"));
                group.setGroupImageURL(results.getString("groupImageURL"));
                group.setStatus(results.getString("status"));
                
                //Get pending posts of a group
                PreparedStatement statementB = connection.prepareStatement("select * from post p join (select groupID from Group_Admin where email = ? and groupId = ?) s on s.groupID = p.groupId and status = 'Pending';");
                statementB.setString(1, email);
                statementB.setInt(2, group.getGroupID());
                
                ResultSet res = statementB.executeQuery();
                
                int count = 0;
                
                while(res.next()){
                    count++;
                }
                
                group.setPendingPosts(count);
                
                groups.add(group);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        // Return the list
        return groups;

    }

}
