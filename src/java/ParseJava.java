import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseJava extends GenericServlet
{
    File myFile;
    PrintWriter pw;
    String fileName,uploadPath,generatePath,db,lang,tag,name,type;
    PrintWriter pwf;
    Elements e;
    @Override
    public void service(ServletRequest req,ServletResponse res)
    {
        try
        {
            res.setContentType("text/html");
            pw=res.getWriter();
            uploadPath = (String)getServletContext().getAttribute("fname");
            generatePath = getServletContext().getRealPath("")+File.separator+"generated";
            File gd = new File(generatePath);
            if(!gd.exists())
                gd.mkdir();
            lang=req.getParameter("langSelect");
            db=req.getParameter("dbSelect");
            pw.println("Lang : "+lang+"&emsp;"+"DB : "+db);

            parse();

            generateDb(db.charAt(0));

            generateLang(lang.charAt(0));
        }
        catch(Exception ex)
        {
            pw.println("Exception : \n"+ex.getMessage());
        }
        finally{
            if(pwf!=null) pwf.close();
            if(pw!=null) pw.close();
        }
    }
    public void parse() throws Exception
    {
        fileName = FilenameUtils.getBaseName(uploadPath);
        pw.println("<h3>Parsing done for,</h3>"+uploadPath+"<br/>");
        Document d = Jsoup.parse(new File(uploadPath),"utf-8");
        e = d.getAllElements();
        pw.println("<h3>Form Details,</h3><br/>");
        for(Element i : e)
        {
            tag=i.tagName();
            name=i.attr("name");
            type=i.attr("type");
            pw.println("tag : "+tag+", name="+name+", type="+type+"<br/>");
        }
    }
    public void generateDb(char ch) throws Exception
    {
        int field=0;
        String query="";
        boolean rfirst=false;
        switch(ch)
        {
            case 'o':
                myFile = new File(generatePath+"\\"+fileName+".dmp");
                pw.println(generatePath+"\\"+fileName+".dmp"+"<br/>");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                query = "create table "+fileName+"(\n";

                for(Element i:e)
                {
                    tag=i.tagName();
                    if(tag.equalsIgnoreCase("textarea")){
                        query += ","+i.attr("name") + " VARCHAR2(4000)";rfirst=false;
                    }
                    else if(tag.equalsIgnoreCase("select"))
                    {
                        query += ","+i.attr("name") +" VARCHAR2(30)";rfirst=false;
                    }
                    else{
                        type=i.attr("type");
                        if(tag.equalsIgnoreCase("input") &&
                            !(type.equalsIgnoreCase("submit")||type.equalsIgnoreCase("button")))
                        {
                            if(type.equalsIgnoreCase("number"))
                            {
                                query += ","+i.attr("name") + " NUMBER";rfirst=false;}
                                else if(type.equalsIgnoreCase("date")){
                                    query += ","+i.attr("name") + " DATE";rfirst=false;}
                                else if(type.equalsIgnoreCase("checkbox")){
                                    query += ","+i.attr("name") + " CHAR(1)";rfirst=false;}
                                else if(type.equalsIgnoreCase("radio"))
                                {
                                    if(!rfirst)
                                    {
                                        rfirst=true;
                                        name=i.attr("name");
                                        query += ","+name + " VARCHAR2(20)";
                                        i.attr("name");
                                    }
                                    else if(!(name.equals(i.attr("name"))))
                                    {
                                        name=i.attr("name");
                                        query += ","+name + " VARCHAR2(20)";
                                        i.attr("name");
                                    }
                            }
                            else{
                                query += ","+i.attr("name") + " VARCHAR2(30)";rfirst=false;}
                        }
                    }
                }
                break;
            case 'm':
                myFile = new File(generatePath+"\\"+fileName+".sql");
                pw.println(generatePath+"\\"+fileName+".sql"+"<br/>");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                query = "create table IF NOT EXISTS "+fileName+"(\n";

                for(Element i:e)
                {
                    tag=i.tagName();
                    if(tag.equalsIgnoreCase("textarea")){
                        query += ",field"+field++ + " text";rfirst=false;
                    }
                    else if(tag.equalsIgnoreCase("select"))
                    {
                        query += ",field"+field++ +" TEXT";rfirst=false;
                    }
                    else{
                        type=i.attr("type");
                        if(tag.equalsIgnoreCase("input") &&
                            !(type.equalsIgnoreCase("submit")||type.equalsIgnoreCase("button")))
                        {
                            if(type.equalsIgnoreCase("number"))
                            {       query += ",field"+field++ + " INT";rfirst=false;}
                            else if(type.equalsIgnoreCase("date")){
                                query += ",field"+field++ + " DATE";rfirst=false;}
                            else if(type.equalsIgnoreCase("checkbox")){
                                query += ",field"+field++ + " CHAR(1)";rfirst=false;}
                            else if(type.equalsIgnoreCase("radio"))
                            {
                                if(!rfirst)
                                {
                                    rfirst=true;
                                    name=i.attr("name");
                                    query += ","+ name + " TEXT";
                                    field++;
                                }
                                else if(!(name.equals(i.attr("name"))))
                                {
                                    name=i.attr("name");
                                    query += ","+name + " TEXT";
                                    field++;
                                }
                            }
                            else{
                                query += ",field"+field++ + " TEXT";rfirst=false;}
                        }
                    }
                }
                break;
            case 'p':
                break;
            default:
                pw.println("none");
                break;
        }
        query += ");";
        query=query.replaceFirst(","," ");
        pwf.write(query);
        pwf.close();
        pw.println("query : "+query);
    }
    public void generateLang(char ch) throws Exception
    {
        String code;
        switch(ch)
        {
            case 'j':
                myFile = new File(generatePath+"\\"+fileName+"ParaRetrieve.java");
                pw.println(generatePath+"\\"+fileName+"ParaRetrieve.java"+"<br/>");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                code = "import java.io.IOException;\n"+
                        "import java.io.PrintWriter;\n"+
                        "import java.sql.Connection;\n"+
                        "import java.sql.DriverManager;\n"+
                        "import java.sql.Statement;\n"+
                        "import java.util.Enumeration;\n"+
                        "import javax.servlet.GenericServlet;\n"+
                        "import javax.servlet.ServletException;\n"+
                        "import javax.servlet.ServletRequest;\n"+
                        "import javax.servlet.ServletResponse;\n"+
                        "public class "+fileName+"ParaRetrieve extends GenericServlet\n"+
                        "{\n"+
                            "Connection con=null;\n"+
                            "String query=null;\n"+
                            "String pname=null,pvalue=null;\n"+
                            "Statement st=null;\n"+
                            "PrintWriter pw = null;\n"+
                            "Enumeration e = null;\n"+
                            "@Override\n"+
                            "public void init()\n"+
                            "{\n"+
                                "try{\n"+
                                    "Class.forName(\"sun.jdbc.odbc.JdbcOdbcDriver\");\n"+
                                    "con=DriverManager.getConnection(\"jdbc:odbc:form\",\"system\",\"mymotog2O\");\n"+
                                    "st=con.createStatement();\n"+
                                "}catch(Exception e)\n"+
                                "{\n"+

                                "}\n"+
                            "}\n"+
                            "@Override\n"+
                            "public void service(ServletRequest req,ServletResponse res)throws ServletException,IOException\n"+
                            "{\n"+
                                "try {\n"+
                                    "res.setContentType(\"text/html\");\n"+
                                    "pw = res.getWriter();\n"+
                                    "e= req.getParameterNames();\n"+
                                    "query=\"insert into "+fileName+" values(\";\n"+
                                    "while(e.hasMoreElements())\n"+
                                    "{\n"+
                                        "pname = (String)e.nextElement();\n"+
                                        "pvalue = req.getParameter(pname);\n"+
                                        
                                        "query += \",'\" + pvalue + \"'\";\n"+
                                    "}\n"+
                                    "query+=\");\";\n"+
                                    "query=query.replaceFirst(\",\",\" \");\n"+
                                    "pw.println(\"***Insert query : \"+query);\n"+
                                "if(st.executeUpdate(query)>0)\n"+
                                        "pw.println(\"inserted\");\n"+
                                "} catch (Exception ex) {\n"+
                                    "pw.println(\"Exception : \"+ex.getMessage());\n"+
                                "}\n"+
                            "}\n"+
                            "@Override\n"+
                            "public void destroy()\n"+
                            "{\n"+
                                "pw.close();\n"+
                                "e=null;\n"+
                                "con=null;\n"+
                                "query=null;\n"+
                                "pname=null;\n"+
                                "pvalue=null;\n"+
                                "st=null;\n"+
                            "}\n"+
                        "}\n";
                pwf.write(code);
                code=code.replace("\n", "<br/>");
                pw.println("<br/><br/>***Java Code : <br/>"+code);
                break;
            case 'p':
                break;
            default:
                pw.println("none");
                break;
        }
    }
}