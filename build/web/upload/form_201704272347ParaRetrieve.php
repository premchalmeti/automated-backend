<?php

$conn = mysql_connect("localhost", "root", "");
mysql_select_db("form_201704272347", $conn);
$i=0;
$query="insert into form_201704272347( txt1,txt2)values( '".htmlspecialchars($_POST['txt1'])."','".htmlspecialchars($_POST['txt2'])."')";
echo "query:".$query;
mysql_query($query) or die("query fail".mysql_error());
echo("your record has been inserted.");?>