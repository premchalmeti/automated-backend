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
import java.sql.SQLException;public class sampleParaRetrieve extends GenericServlet
{
Connection con=null;
String iquery;
Statement st=null;
PrintWriter pw = null;
@Override
public void init()
{
try{
Class.forName("oracle.jdbc.driver.OracleDriver");
con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","shubham99S");
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
iquery="insert into sample( as) values ( '"+req.getParameter("as")+"')";
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
