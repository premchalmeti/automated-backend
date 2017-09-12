<?php session_start();
    $_SESSION['id'] = session_id();
?>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Home</title>
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <style>
            .myButton1 {
                background-color:#44c767;
                -moz-border-radius:28px;
                -webkit-border-radius:28px;
                border-radius:28px;
                border:1px solid #18ab29;
                display:inline-block;
                cursor:pointer;
                color:black;
                font-family:Arial;
                font-size:17px;
                padding:30px 60px;
                text-decoration:none;
                text-shadow:0px 1px 0px #2f6627;
            }
            .myButton1:hover{
                background-color: rgb(150, 150, 150);
            }
            .myButton1:active {
                position:relative;
                top:4px;
            }
            #snackbar {
                border-radius: 20px;
                box-shadow: 0px 4px 20px slategrey;
                visibility: hidden;
                width:fit-content;
                min-width: 250px;
                margin-left: -125px;
                background-color: #333;
                color: #fff;
                text-align: center;
                border-radius: 2px;
                padding: 16px;
                position: fixed;
                z-index: 1;
                left: 50%;
                bottom: 30px;
                font-size: 17px;
            }
            #snackbar.show {
                visibility: visible;
                -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
                animation: fadein 0.5s, fadeout 0.5s 2.5s;
            }
            @-webkit-keyframes fadein {
                from {bottom: 0; opacity: 0;} 
                to {bottom: 30px; opacity: 1;}
            }

            @keyframes fadein {
                from {bottom: 0; opacity: 0;}
                to {bottom: 30px; opacity: 1;}
            }
            @-webkit-keyframes fadeout {
                from {bottom: 30px; opacity: 1;} 
                to {bottom: 0; opacity: 0;}
            }
            @keyframes fadeout {
                from {bottom: 30px; opacity: 1;}
                to {bottom: 0; opacity: 0;}
            }
        </style>
    </head>
    <body> 
        <div class="container">
            <header>
                <h1>Automated Backend Generation for HTML Forms <br><br></h1>
            </header>
            <div style="border-radius:10px;" align="center" id="snackbar"><label id="txt">file uploaded</label></div>
            <div id="container_demo">
                <div id="wrapper">
                    <div id="login" class="animate form">
                        <h1>Log in</h1>
                        <a href="formbuilder.php">
                            <input name="button" type="submit" class="myButton1" value="Build your form" style="box-shadow: 0px 4px 10px slategrey;"> <br/><br/>
                        </a>
                        <form action="http://localhost:8080/backend_GEN/file" method="post" enctype="multipart/form-data">    
                            <input type="file" accept="text/html" name="uploadFile" id="f1" class="myButton1" style="box-shadow: 0px 4px 10px slategrey;">
                    </div>
                </div>
                 <input type="submit" onclick="makeToast(event)" class="myButton2" title="Next" value=">>">
            </form>
            </div> 
        </div>
        
        <script>
        function makeToast(event){ 
            var x = document.getElementById("snackbar");
            x.className = "show";            
            if(document.getElementById('f1').value)
            {   
                document.getElementById("txt").innerHTML="file uploaded";
            }
            else
            {
                event.preventDefault();
                document.getElementById("txt").innerHTML="Please choose file";
            }
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
         }
        </script>
    </body>
</html>