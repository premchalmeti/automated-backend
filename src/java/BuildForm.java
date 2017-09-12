import javax.servlet.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class BuildForm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException 
    {
        String UPLOAD_DIRECTORY = "upload";
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        PrintWriter pw = res.getWriter();
        String TimeStampBasedFileName = new SimpleDateFormat("'form_'yyyyMMddHHmm'.html'").format(new Date());
        String fileName=uploadPath+File.separator+TimeStampBasedFileName;
        getServletContext().setAttribute("fname", fileName);
        File uploadDir=new File(uploadPath);
        File my=new File(fileName);
        if(!my.exists())
        {
               my.createNewFile();
               pw.println("created");
        }
        my.delete();
        my.createNewFile();
        String txt = req.getParameter("editor1");
        FileWriter fw=new FileWriter(my);
        fw.write(txt);
        res.setContentType("text/html");
        if(!uploadDir.exists())
        {
            uploadDir.mkdir();
            pw.println("created");
        }
        pw.println(txt);
        pw.println(uploadPath);
        fw.close();
        res.sendRedirect(res.encodeRedirectURL("http://localhost:80/backend_GEN/web/select.php?fn="+fileName));
    }   
}
