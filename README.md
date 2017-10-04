# Automated backend generation for HTML forms
Technologies used : 
CKEditor, HTML5, CSS3, BootStrap, JSoup API, JavaScipt, jQuery, Java Servlet and PHP.

The idea of the project comes under the topic of <b>opportunistic programming</b>.

It is a ubiquitous system that automatically generates the backend for HTML forms. The user either can upload an existing HTML form or create one right into the system using an editor called <a href="https://ckeditor.com/">CKEditor</a> which is a <a href="http://whatis.techtarget.com/definition/WYSIWYG-what-you-see-is-what-you-get">WYSIWYG(What You See Is What You Get)</a> type of editor. 

The user gets 3 choices for a serverside scripting program and 3 choices for a database.

User has following choices,

*Serverside scripting language,
1. Java (Servlet)
2. PHP.
3. ASP.net

*Database,
1. Oracle
2. PostgreSQL
3. MySQL

After selecting both choices, the HTML Form is then parsed by a java program written using a JSoup API and then backend for HTML is generated.

The Final output will be a zip file contains,
1. HTML form.
2. Server-side scripting program.
3. SQL file.
