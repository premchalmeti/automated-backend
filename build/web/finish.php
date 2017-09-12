<?php
    $fileName=htmlspecialchars($_GET['fileName']);
    $lang= htmlspecialchars($_GET['lang']);
    $db=htmlspecialchars($_GET['db']);
    session_start();
    if(!isset($_SESSION['id'])){
        header("Location: http://localhost:80/backend_GEN/web/home.php");
        exit();
    }
    echo '
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Download</title>
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
    </head>
    <body style="font-family:Arial;"> 
        <div class="container">
            <header>
                <h1>Automated Backend Generation for HTML Forms  <br><br><br><br><br></h1>
            </header>
            <table align="center">
                <tr>
                    <td><img src="images\winrar.png" alt="images/winrar logo" width="100"/></td>
                    <td><strong><label>Your file will be downloaded..</label></strong></td>
                </tr>
                <tr>
                    <td><b><label>File Name : </label></b></td>
                    <td><label>'.$fileName.'</label></td>
                </tr>
                <tr>
                    <td><b><label>Language selected : </label></b></td>
                    <td><label id="lang">'.$lang.'</label></td>
                </tr>
                <tr>
                    <td><b><label>Database selected : </label></b></td>
                    <td><label>'.$db.'</label></td>
                </tr>
            </table>
            <div id="base">
                <a href="home.php"><input  type="submit" class="myButton2" value="<<Back to Home"></a>
                <a href="http://localhost:80/backend_GEN/build/web/upload/createZIP.php?fileName='.$fileName.'&lang='.$lang.'&db='.$db.'"> <input  type="button" class="myButton2" value="Download" ></a>
            </div>
        </div>
    </body>
    <style>
        div#base{
            padding:20px;
        }
        td{
            padding:20px;
            color:black;
        }
    </style>
</html>
';