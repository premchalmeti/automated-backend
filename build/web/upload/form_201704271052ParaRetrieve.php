<?php

$conn = mysql_connect("localhost", "root", "");
mysql_select_db("form_201704271052", $conn);
$i=0;
$query="insert into form_201704271052( )values( '".htmlspecialchars($_POST[''])."')";
echo "query:".$query;
mysql_query($query) or die("query fail".mysql_error());
echo("your record has been inserted.");?>