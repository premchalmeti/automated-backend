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

public class ParseFields extends GenericServlet
{
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

//            generateLang(lang.charAt(0));
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
        File myFile;
        String query=null;

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
        pw.println("query : "+query);
        pwf.write(query);
    }
    public void generateLang(char ch) throws Exception
    {
        switch(ch)
        {
            case 'j':
                break;
            case 'a':
                break;
            case 'p':
                break;
            default:
                pw.println("none");
                break;
        }
    }
}