<?php

$conn = mysql_connect("localhost", "root", "");
mysql_select_db("form", $conn);
$i=0;
$query="insert into form( txt1,pwd,mail)values( '".htmlspecialchars($_POST['txt1'])."','".htmlspecialchars($_POST['pwd'])."','".htmlspecialchars($_POST['mail'])."')";
echo "query:".$query;
mysql_query($query) or die("query fail".mysql_error());
echo("your record has been inserted.");?>