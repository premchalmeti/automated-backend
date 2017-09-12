<?php

$conn = mysql_connect("localhost", "root", "");
mysql_select_db("sampleform", $conn);
$i=0;
if (!isset($_POST['vehicle1'])) {
$var0= "null";
}
else
{
    $var0=htmlspecialchars($_POST['vehicle1']);
}
if (!isset($_POST['vehicle2'])) {
$var1= "null";
}
else
{
    $var1=htmlspecialchars($_POST['vehicle2']);
}
$query="insert into sampleform( nmtxt,pwdtxt,mailtxt,usrtel,homepage,agetxt,dobtxt,vehicle1,vehicle2,gender,cars,message)values( '".htmlspecialchars($_POST['nmtxt'])."','".htmlspecialchars($_POST['pwdtxt'])."','".htmlspecialchars($_POST['mailtxt'])."','".htmlspecialchars($_POST['usrtel'])."','".htmlspecialchars($_POST['homepage'])."',".htmlspecialchars($_POST['agetxt']).",'".htmlspecialchars($_POST['dobtxt'])."','".$var0."','".$var1."','".htmlspecialchars($_POST['gender'])."','".htmlspecialchars($_POST['cars'])."','".htmlspecialchars($_POST['message'])."')";
echo "query:".$query;
mysql_query($query) or die("query fail".mysql_error());
echo("your record has been inserted.");?>