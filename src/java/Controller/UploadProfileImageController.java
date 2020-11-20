package Controller;

import Modal.HandleUser;
import Modal.User;
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

public class UploadProfileImageController extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private final int maxFileSize = 50 * 1024 * 1024;
    private final int maxMemSize = 4 * 1024;
    private File file;

    @Override
    public void init() {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload-profile-image");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();

        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

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
                if (!fi.isFormField()) {

                    //Create File Name
                    Random random = new Random();
                    HttpSession session = request.getSession();
                    String fileName = String.format("%s-%d-%s", (String) session.getAttribute("email"), random.nextInt(1000000000), fi.getName());

                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);

                    User user = new User();
                    user.setEmail((String) session.getAttribute("email"));
                    user.setProfileImageURL(String.format("http://localhost:8080/\\data\\profileImages\\%s", fileName));

                    HandleUser handleUser = new HandleUser();
                    handleUser.updateProfileImage(user);

                    response.sendRedirect("GetProfileController");

                }
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
    }

}
