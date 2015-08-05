<?php
require_once('DB.php');

header("Content-Type: text/plain");

$dsn = array(
    'phptype' => 'mysql', 
    'username' => 'j178083rw',
    'password' => 'a1289b', 
    'hostspec' => 'mysql4-j',
    'database' => 'j178083_jetris'
);

$db =& DB::connect($dsn);
if(PEAR::isError($db)) {
    die($db->getMessage());
}

$getpass = $_GET['pass'];

$gethiscore = $_GET['sc'];
$gethilines = $_GET['ln'];
$getnamebase64 = $_GET['name'];

if(!isset($getpass) || $getpass != '26195') {
    die("You have no right to update the Database!");
}

if(!isset($gethiscore) || $gethiscore == "" ||
   !isset($gethilines) || $gethilines == "" ||
   !isset($getnamebase64) || $getnamebase64 == "") {
    
    die("Some required data is missing!");
}

$sqlSelect = "SELECT id FROM hiscore WHERE hiscore = $gethiscore AND hilines = $gethilines AND name = '$getnamebase64'";
$result =& $db->query($sqlSelect); 
if(PEAR::isError($result)){
    die ($db->getMessage());
}

if($result->fetchInto($row,DB_FETCHMODE_ASSOC)) {
    die("ok"); // NO DUPLICATES
}

$sqlInsert = "INSERT INTO hiscore (hiscore, hilines, name) VALUES($gethiscore, $gethilines, '$getnamebase64')";

$result =& $db->query($sqlInsert); 
if(PEAR::isError($result)){
    die ($db->getMessage());
}

$db->disconnect();

?>
ok


