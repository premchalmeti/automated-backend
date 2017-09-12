<?php

$zip = new ZipArchive();
$fname = "./" . time() . "_backend.zip";
if ($zip->open($fname, ZipArchive::CREATE) !== TRUE) {
    exit("cannot open<$fname>\n");
}
$fileName = $_GET['fileName'];
$lang = $_GET['lang'];
$db = $_GET['db'];
//echo $fileName.'.html'.$lang.$db;
$zip->addFile($fileName . '.html'); //add html file

if (!strcmp($lang, "java")) {
    $langName = $fileName . 'ParaRetrieve.java';
} else if (!strcmp($lang, "php")) {
    $langName = $fileName . 'ParaRetrieve.php';
}
$zip->addFile($langName); //add lang file

$baseName = $fileName;

if (!strcmp($db, "mysql")) {
    $fileName = $fileName . '.sql';
} else if (!strcmp($db, "oracle")) {
    $fileName = $fileName . '.dmp';
} else if (!strcmp($db, "postgresql")) {
    $fileName = $fileName . '.sql';
}
$zip->addFile($fileName); //add sql file

if(!(strcmp($lang,'php') and strcmp($db,'mysql')))
{
    $readmetxt = '      
Now your have a '.$fname.' file which contains 3 files,

namely

1.'.$baseName.'.html
2.'.$baseName.'ParaRetrieve.php
3.'.$baseName.'.sql

Steps to be followed : 

step 1:	Goto your phpmyadmin page and create database named "'.$baseName.'".

step 2:	Import the '.$baseName.'.sql into '.$baseName.' database.

step 3: Now place '.$baseName.'.html and '.$baseName.'ParaRetrieve.php into default web server directory.

step 4: Enter '.$baseName.'.html http://www.something.com/'.$baseName.'.html into your browser.
		sample URL : http://php.net/sampleForm.html';
    
$fptr = fopen("readMe.txt","w");
fwrite($fptr, $readmetxt);
fclose($fptr);
$zip->addFile("readMe.txt");
}
$zip->close();

echo "created";
header("Content-type:application/zip");
header("Content-Disposition:attachment;filename=$fname");
header("Pragma: no-cache");
header("Expires:0");
readfile("$fname");
