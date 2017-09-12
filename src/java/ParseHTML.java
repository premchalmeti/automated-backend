import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHTML extends HttpServlet
{
    String fileName,uploadPath,generatePath,db,lang,tag,name,type;
    String iquery="",piquery="",jiquery="",pvalues="",pcondition="";
    PrintWriter pwf,pw;
    File myFile;
    int cnt=0;
    Elements input,textarea,select,radio;
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)
    {
        try
        {
            res.setContentType("text/html");
            pw=res.getWriter();
            uploadPath = (String)getServletContext().getAttribute("fname");//full path either from formbuilder or fileupload 
            generatePath = getServletContext().getRealPath("")+File.separator+"upload";
            File gd = new File(generatePath);
            if(!gd.exists())
                gd.mkdir();
            
            lang = req.getParameter("langSelect");
            db = req.getParameter("dbSelect");
            
            fileName = FilenameUtils.getBaseName(uploadPath);//filename except extension
            parse();
            dbcode(db.charAt(0),lang.charAt(0));
            generateLang(lang.charAt(0),db.charAt(0));
            res.sendRedirect(res.encodeRedirectURL("http://localhost:80/backend_GEN/web/finish.php?lang="+lang+"&db="+db+"&fileName="+fileName));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally{
            if(pwf!=null) pwf.close();
        }
    }
    public void parse() throws Exception
    {
        Document d = Jsoup.parse(new File(uploadPath),"utf-8");
        
        input = d.getElementsByTag("input");
        input = input.not("[type=submit],[type=button],[type=reset]");
        radio = input.select("[type=radio]");
        input = input.not("[type=radio]");
        textarea = d.getElementsByTag("textarea");
        select = d.getElementsByTag("select");
    }
    public void dbcode(char db,char lang)throws Exception
    {
        String cquery="";
        switch(db){
            case 'o':
                myFile = new File(generatePath+"\\"+fileName+".dmp");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                cquery = "create table "+fileName+"(\n";
                switch (lang) {
                    case 'j':
                        for(Element i:input)
                        {
                            type=i.attr("type");
                            name = i.attr("name");
                            cquery += ",\n" + name;
                            if(type.equalsIgnoreCase("number"))
                            {
                                cquery += " NUMBER";
                                iquery += ","+name;
                                jiquery += String.format(",\"+%s+\"",String.format("req.getParameter(\"%s\")", name));
                            }
                            else if(type.equalsIgnoreCase("date")){
                                cquery += " DATE";
                                iquery += ","+name;
                                jiquery += String.format(",to_date('\"+%s+\"','yyyy/mm/dd')",String.format("req.getParameter(\"%s\")",name));
                            }
                            else if(type.equalsIgnoreCase("checkbox"))
                            {
                                cquery += " VARCHAR2(20)";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                            else{
                                cquery += " VARCHAR2(30)";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        input=null;
                        name="";
                        for(Element i:radio)
                        {
                            if(name.equals(""))
                            {
                                name=i.attr("name");
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                                cquery += ",\n"+name + " VARCHAR2(20)";
                            }
                            else if(!(name.equalsIgnoreCase(i.attr("name")))){
                                name=i.attr("name");
                                iquery += ","+name;
                                cquery += ",\n"+name + " VARCHAR2(20)";
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        radio=null;
                        for(Element i:select)
                        {
                            name=i.attr("name");
                            iquery += ","+name;
                            cquery += ",\n"+ name + " VARCHAR2(20)";
                            jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                        }
                        select=null;
                        for(Element i:textarea)
                        {
                            name=i.attr("name");
                            iquery += ","+name;
                            cquery += ",\n"+name + " VARCHAR2(4000)";
                            jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                        }
                        textarea=null;
                        break;
                    case 'p':
//                        for(Element i:input)
//                        {
//                            type=i.attr("type");
//                            name = i.attr("name");
//                            cquery += ",\n" + name;
//                            if(type.equalsIgnoreCase("number"))
//                            {
//                                cquery += " NUMBER";
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                pvalues+=",\".htmlspecialchars($_POST['"+name+"']).\"";
//                            }
//                            else if(type.equalsIgnoreCase("date")){
//                                cquery += " DATE";
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                            }
//                            else if(type.equalsIgnoreCase("checkbox"))
//                            {
//                                cquery += " VARCHAR2(20)";
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                pcondition +="if (!isset($_POST['"+name+"'])) {\n"
//                                        + "$var"+cnt+"= \"null\";\n"
//                                        + "}\n"
//                                        + "else\n"
//                                        + "{\n"
//                                        + "    $var"+cnt+"=htmlspecialchars($_POST['"+name+"']);\n"
//                                        + "}\n";
//                                pvalues+=",'\".$var"+cnt+".\"'";
//                                ++cnt;
//                            }
//                            else{
//                                cquery += " VARCHAR2(30)";
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                            }
//                        }
//                        cnt=0;
//                        input=null;
//                        name="";
//                        for(Element i:radio)
//                        {
//                            if(name.equals(""))
//                            {
//                                name=i.attr("name");
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                cquery += ",\n"+name + " VARCHAR2(20)";
//                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                            }else if(!(name.equalsIgnoreCase(i.attr("name")))){
//                                name=i.attr("name");
//                                iquery += ","+name;
//                                piquery += ","+name;
//                                cquery += ",\n"+name + " VARCHAR2(20)";
//                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                            }
//                        }
//                        radio=null;
//                        for(Element i:select)
//                        {
//                            name=i.attr("name");
//                            iquery += ","+name;
//                            piquery += ","+name;
//                            cquery += ",\n"+ name + " VARCHAR2(20)";
//                            pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                        }
//                        select=null;
//                        for(Element i:textarea)
//                        {
//                            name=i.attr("name");
//                            iquery += ","+name;
//                            piquery += ","+name;
//                            cquery += ",\n"+name + " VARCHAR2(4000)";
//                            pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
//                        }
//                        textarea=null;
                        break;
                    case 'a':
                        break;
                }
                break;
            case 'm':
//                iquery="";
                piquery="";
                pvalues="";
                myFile = new File(generatePath+"\\"+fileName+".sql");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                cquery = "create table IF NOT EXISTS "+fileName+"(\n";
                switch (lang) {
                    case 'p':
                        cnt=0;
                        for(Element i:input)
                        {
                            type=i.attr("type");
                            name = i.attr("name");
                            cquery += ",\n" + name;
                            if(type.equalsIgnoreCase("number"))
                            {
                                cquery += " INT";
//                                iquery += ","+name;
                                piquery += ","+name;
                                pvalues+=",\".htmlspecialchars($_POST['"+name+"']).\"";
                            }
                            else if(type.equalsIgnoreCase("date")){
                                cquery += " DATE";
//                                iquery += ","+name;
                                piquery += ","+name;
                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                            }
                            else if(type.equalsIgnoreCase("checkbox"))
                            {
                                cquery += " CHAR(20)";
//                                iquery += ","+name;
                                piquery += ","+name;
                                pcondition +="if (!isset($_POST['"+name+"'])) {\n"
                                        + "$var"+cnt+"= \"null\";\n"
                                        + "}\n"
                                        + "else\n"
                                        + "{\n"
                                        + "    $var"+cnt+"=htmlspecialchars($_POST['"+name+"']);\n"
                                        + "}\n";
                                pvalues+=",'\".$var"+cnt+".\"'";
                                ++cnt;
                            }
                            else{
                                cquery += " TEXT";
//                                iquery += ","+name;
                                piquery += ","+name;
                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                            }
                        }
                        input=null;
                        name="";
                        for(Element i:radio)
                        {
                            if(name.equals(""))
                            {
                                name=i.attr("name");
//                                iquery += ","+name;
                                piquery += ","+name;
                                cquery += ",\n"+name + " TEXT";
                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                            }else if(!(name.equalsIgnoreCase(i.attr("name")))){
                                name=i.attr("name");
//                                iquery += ","+name;
                                piquery += ","+name;
                                cquery += ",\n"+name + " TEXT";
                                pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                            }
                        }
                        radio=null;
                        for(Element i:select)
                        {
                            name=i.attr("name");
                            cquery += ",\n"+ name + " TEXT";
//                            iquery += ","+name;
                            piquery += ","+name;
                            pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                        }
                        select=null;
                        for(Element i:textarea)
                        {
                            name=i.attr("name");
                            cquery += ",\n"+name + " TEXT";
//                            iquery += ","+name;
                            piquery += ","+name;
                            pvalues+=",'\".htmlspecialchars($_POST['"+name+"']).\"'";
                        }
                        textarea=null;
                        break;
        
                    case 'j':
                        for(Element i:input)
                        {
                            type=i.attr("type");
                            name = i.attr("name");
                            cquery += ",\n" + name;
                            if(type.equalsIgnoreCase("number"))
                            {
                                cquery += " INT";
                                iquery += ","+name;
                                jiquery += String.format(",\"+%s+\"",String.format("req.getParameter(\"%s\")", name));
                            }
                            else if(type.equalsIgnoreCase("date")){
                                cquery += " DATE";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")",name));
                            }
                            else if(type.equalsIgnoreCase("checkbox"))
                            {
                                cquery += " CHAR(20)";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                            else{
                                cquery += " TEXT";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        name="";
                        for(Element i:radio)
                        {
                            if(name.equals(""))
                            {
                                name=i.attr("name");
                                cquery += ",\n"+name + " TEXT";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }else if(!name.equalsIgnoreCase(i.attr("name"))){
                                name=i.attr("name");
                                cquery += ",\n"+name + " TEXT";
                                iquery += ","+name;
                                jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        for(Element i:select)
                        {
                            name=i.attr("name");
                            cquery += ",\n"+ name + " TEXT";
                            iquery += ","+name;
                            jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                        }
                        for(Element i:textarea)
                        {
                            name=i.attr("name");
                            cquery += ",\n"+name+ " TEXT";
                            iquery += ","+name;
                            jiquery += String.format(",'\"+%s+\"'",String.format("req.getParameter(\"%s\")", name));
                        }
                        break;
                    case 'a':
                        break;
                }
                break;
            case 'p':
                iquery="";
                piquery="";
                pvalues="";
                myFile = new File(generatePath+"\\"+fileName+".sql");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                cquery = "create table IF NOT EXISTS "+fileName+"(\n";
                switch (lang) {
                    case 'p':
                            break;
                    case 'j':
                        for (Element i : input) {
                            type = i.attr("type");
                            name = i.attr("name");
                            cquery += ",\n" + name;
                            if (type.equalsIgnoreCase("number")) {
                                cquery += " INT";
                                iquery += "," + name;
                                jiquery += String.format(",\"+%s+\"", String.format("req.getParameter(\"%s\")", name));
                            } else if (type.equalsIgnoreCase("date")) {
                                cquery += " DATE";
                                iquery += "," + name;
                                jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                            } else if (type.equalsIgnoreCase("checkbox")) {
                                cquery += " CHAR(20)";
                                iquery += "," + name;
                                jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                            } else {
                                cquery += " TEXT";
                                iquery += "," + name;
                                jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        name = "";
                        for (Element i : radio) {
                            if (name.equals("")) {
                                name = i.attr("name");
                                cquery += ",\n" + name + " TEXT";
                                iquery += "," + name;
                                jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                            } else if (!name.equalsIgnoreCase(i.attr("name"))) {
                                name = i.attr("name");
                                cquery += ",\n" + name + " TEXT";
                                iquery += "," + name;
                                jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                            }
                        }
                        for (Element i : select) {
                            name = i.attr("name");
                            cquery += ",\n" + name + " TEXT";
                            iquery += "," + name;
                            jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                        }
                        for (Element i : textarea) {
                            name = i.attr("name");
                            cquery += ",\n" + name + " TEXT";
                            iquery += "," + name;
                            jiquery += String.format(",'\"+%s+\"'", String.format("req.getParameter(\"%s\")", name));
                        }
                        break;
                    case 'a':
                            break;
                }
                break;
        }
        cquery += ");";
        cquery=cquery.replaceFirst(","," ");
        
        iquery += ")";  //column closed
        iquery=iquery.replaceFirst(","," ");
        if(lang=='j')
        {
            jiquery=jiquery.replaceFirst(","," ");
            iquery += " values ("+jiquery;
            iquery = String.format("%s)\"",iquery);
        }
        pwf.write(cquery);
        pwf.close();
    }
    public void generateLang(char lang,char db) throws Exception
    {
        String code;
        String ConString="";
        switch(lang)
        {
            case 'j':
                switch (db) {
                    case 'o':
                        ConString="Class.forName(\"oracle.jdbc.driver.OracleDriver\");\n"+
                                "con=DriverManager.getConnection(\"jdbc:oracle:thin:@localhost:1521:XE\",\"system\",\"shubham99S\");\n";
                        break;
                    case 'm':
                        ConString="Class.forName(\"com.mysql.jdbc.Driver\");\n" +
                                "con=DriverManager.getConnection(\"jdbc:mysql://localhost:3306/"+fileName+"\", \"root\", \"\");";
                        break;
                    case 'p':
                        ConString = "Class.forName(\"org.postgresql.Driver\");\n"
                                + "con = DriverManager.getConnection(\"jdbc:postgresql://localhost:5432/"+fileName+"\",\"postgres\", \"shubham99S\");\n";
                        break;
                    default:
                        break;
                }
                myFile = new File(generatePath+"\\"+fileName+"ParaRetrieve.java");
                if(myFile.exists())
                        myFile.delete();
                myFile.createNewFile();
                pwf=new PrintWriter(new FileWriter(myFile),true);
                code =  "import java.io.IOException;\n"+
                        "import java.io.PrintWriter;\n"+
                        "import java.sql.Connection;\n"+
                        "import java.sql.DriverManager;\n"+
                        "import java.sql.Statement;\n"+
                        "import javax.servlet.GenericServlet;\n"+
                        "import javax.servlet.ServletException;\n"+
                        "import javax.servlet.ServletRequest;\n"+
                        "import javax.servlet.ServletResponse;\n"+
                        "import java.util.logging.Level;\n" +
                        "import java.util.logging.Logger;\n" +
                        "import java.sql.SQLException;"+
                        "public class "+fileName+"ParaRetrieve extends GenericServlet\n"+
                        "{\n"+
                            "Connection con=null;\n"+
                            "String iquery;\n"+
                            "Statement st=null;\n"+
                            "PrintWriter pw = null;\n"+
                            "@Override\n"+
                            "public void init()\n"+
                            "{\n"+
                                "try{\n"+
                                    ConString+                                   
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
                                    "iquery=\"insert into "+fileName+"("  +iquery+";\n"+
                                    "pw.println(\"***Insert query : \"+iquery);\n"+
                                "if(st.executeUpdate(iquery)>0)\n"+
                                        "pw.println(\"inserted\");\n"+
                                "} catch (Exception ex) {\n"+
                                    "pw.println(\"Exception : \"+ex.getMessage());\n"+
                                "}\n"+
                            "}\n"+
                            "@Override\n"+
                            "public void destroy()\n"+
                            "{\n"+
                                "pw.close();\n"+
                                "try {\n"
                               + "        con.close();\n"
                               + "    } catch (SQLException ex) {\n"
                               + "        Logger.getLogger(sampleformParaRetrieve.class.getName()).log(Level.SEVERE, null, ex);\n"
                               + "    }"
                               +                           "iquery=null;\n"+
                                "st=null;\n"+
                            "}\n"+
                        "}\n";
                pwf.write(code);
                break;
            case 'a':
                break;
            case 'p':
                piquery=piquery.replaceFirst(",", " ");
                pvalues=pvalues.replaceFirst(",", " ");
                myFile = new File(generatePath + "\\" + fileName + "ParaRetrieve.php");
                if (myFile.exists()) {
                    myFile.delete();
                }
                myFile.createNewFile();
                pwf = new PrintWriter(new FileWriter(myFile), true);
                code = "<?php\n"
                        + "\n"
                        + "$conn = mysql_connect(\"localhost\", \"root\", \"\");\n"
                        + "mysql_select_db(\"" + fileName + "\", $conn);\n"
                        +"$i=0;\n"
                        +pcondition
                        + "$query=\"insert into "+fileName+"(" + piquery + ")values(" + pvalues + ")\";\n"
                        + "echo \"query:\".$query;\n"
                        + "mysql_query($query) or die(\"query fail\".mysql_error());\n"
                        + "echo(\"your record has been inserted.\");"
                        + "?>";
                pwf.write(code);
                pwf.close();
                pcondition="";
                piquery="";
                pvalues="";
                break;
        }
    }
}