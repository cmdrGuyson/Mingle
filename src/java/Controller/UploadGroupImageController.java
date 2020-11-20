package Controller;

import Modal.Group;
import Modal.HandleGroup;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadGroupImageController extends HttpServlet {

    private String filePath;
    private final int maxFileSize = 50 * 1024 * 1024;
    private final int maxMemSize = 4 * 1024;
    private File file;

    @Override
    public void init() {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload-group-image");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        uploadGroupImage(request, response);

    }

    private void uploadGroupImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Group group = new Group();
        
        int groupID = 0;
        

        // Check that we have a file upload request
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            System.out.println(fileItems.size());

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (fi.isFormField()) {
                    
                    String fieldName = fi.getFieldName();
                    String fieldValue = fi.getString();
                    
                    
                    if(fieldName.equals("groupID")){
                        groupID = Integer.parseInt(fieldValue);
                        group.setGroupID(groupID);
                    }
                    
                } else {

                    //Create File Name
                    Random random = new Random();
                    String fileName = String.format("group-%d-%d-%s", groupID, random.nextInt(1000000000), fi.getName());

                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    
                    group.setGroupImageURL(String.format("http://localhost:8080/\\data\\groupImages\\%s", fileName));

                }
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        
        HandleGroup handleGroup = new HandleGroup();
        
        handleGroup.changeGroupImageURL(group);

        HttpSession session = request.getSession();

        boolean isGroupAdmin = handleGroup.isGroupAdmin(groupID, (String) session.getAttribute("email"));

        request.setAttribute("group", handleGroup.getSingleGroup(groupID));
        request.setAttribute("isGroupAdmin", isGroupAdmin);

        //Direct the user back to the group page
        request.setAttribute("groupID", groupID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ViewSingleGroupController");
        dispatcher.forward(request, response);

    }

}
