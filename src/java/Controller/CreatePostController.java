package Controller;

import Modal.HandlePost;
import Modal.Post;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CreatePostController extends HttpServlet {

    private String filePath;
    private final int maxFileSize = 50 * 1024 * 1024;
    private final int maxMemSize = 4 * 1024;
    private File file;

    @Override
    public void init() {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload-post-image");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        createPost(request, response);

    }

    private void createPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Post post = new Post();
        
        HttpSession session = request.getSession();
        post.setUserEmail((String) session.getAttribute("email"));
        

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
                    
                    // set post object's values according to user input
                    if(fieldName.equals("postBody")){
                        System.out.println(fieldValue);
                        post.setPostBody(fieldValue);
                    }else if(fieldName.equals("privacy")){
                        post.setPrivacy(fieldValue);
                        System.out.println(fieldValue);
                    }
                    

                } else {

                    //Create File Name
                    Random random = new Random();
                    String fileName = String.format("post-%d-%s-%d-%s", random.nextInt(1000000000), (String) session.getAttribute("email"), random.nextInt(1000000000), fi.getName());

                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    
                    //Set post image URL as the newly upload image
                    post.setPostImageURL(String.format("http://localhost:8080/\\data\\postImages\\%s", fileName));

                }
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        
        HandlePost handlePost = new HandlePost();
        
        handlePost.createPost(post);
        

        response.sendRedirect("SendHomeController");

    }

}
