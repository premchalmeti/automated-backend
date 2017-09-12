<?php session_start();
    if(!isset($_SESSION['id'])){
        header("Location: http://localhost:80/backend_GEN/web/home.php");
        exit();
    }
    
?>
<html>
    
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Select</title>
    <link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body style="font-family:Arial;">
    <div class="container">
    <header>
        <h1>Automated Backend Generation for HTML Forms</h1>
    </header>
    <form id="selectForm" action="http://localhost:8080/backend_GEN/ParseHTML" method="post">
    <center>
    <table>
    <tr>
    <td>
        <div title="Select language:" id="langselect" class="box">
            <table>
                <td><label>Select Language:&emsp;&emsp;</label>	</td>
                <td>
                    <select name="langSelect" >
                        <option value="java">Java</option>
                        <option value="asp.net">ASP.net</option>
                        <option value="php">PHP</option>
                        <option value="none" selected>none</option>
                    </select>
                </td>
            </table>
        </div>
    </td>
    <td>
	<div title="Select Database:" id="dbselect" class="box">
            <table>
                <td><label>Select Database:&emsp;&emsp;</label>	</td>
                <td>
                    <select name="dbSelect" >
                        <option value="oracle">Oracle</option>
                        <option value="mysql">mySQL</option>
                        <option value="postgresql">postgreSQL</option>
                        <option value="none" selected>none</option>
                        </select>
                    </td>
            </table>
	</div>
    </td>
    </tr>
    </table>
    </center>
    <br><br><br><br>
    <div align="center">
    <table>
        <tr>
            <td>
                <a href="http://localhost:80/backend_GEN/web/home.php"><input type="button" class="myButton2" title="Back" value="<<" name="cancelbt" > </a>
            </td>
            <td style="width:50px;"></td>
            <td>
                <input type="submit" class="myButton2" title="Next" value=">>" name="nxtbt">
    </form>
            </td>	
        </tr>
    </table>
    </div>
</body>
</html>