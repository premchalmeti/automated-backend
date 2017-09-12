import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
public class sampleformParaRetrieve extends GenericServlet
{
Connection con=null;
String iquery;
Statement st=null;
PrintWriter pw = null;
@Override
public void init()
{
try{
Class.forName("org.postgresql.Driver");
con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sampleform","postgres", "shubham99S");
st=con.createStatement();
}catch(Exception e)
{
}
}
@Override
public void service(ServletRequest req,ServletResponse res)throws ServletException,IOException
{
try {
res.setContentType("text/html");
pw = res.getWriter();
iquery="insert into sampleform( nmtxt,pwdtxt,mailtxt,usrtel,homepage,agetxt,dobtxt,vehicle1,vehicle2,gender,cars,message) values ( '"+req.getParameter("nmtxt")+"','"+req.getParameter("pwdtxt")+"','"+req.getParameter("mailtxt")+"','"+req.getParameter("usrtel")+"','"+req.getParameter("homepage")+"',"+req.getParameter("agetxt")+",'"+req.getParameter("dobtxt")+"','"+req.getParameter("vehicle1")+"','"+req.getParameter("vehicle2")+"','"+req.getParameter("gender")+"','"+req.getParameter("cars")+"','"+req.getParameter("message")+"')";
pw.println("***Insert query : "+iquery);
if(st.executeUpdate(iquery)>0)
pw.println("inserted");
} catch (Exception ex) {
pw.println("Exception : "+ex.getMessage());
}
}
@Override
public void destroy()
{
pw.close();
try {
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(sampleformParaRetrieve.class.getName()).log(Level.SEVERE, null, ex);
    }iquery=null;
st=null;
}
}
