<?php
include "templateUp.php";
?>
    <h1>HiScore</h1>
	
	<p><u>If You get an error try reloading the Page.</u><br>&nbsp;</p>
	
    <h2>TOP 100</h2>
	
	<table WIDTH=100% BORDER=0> 
		<tr>
			<td width="50">&nbsp;</td>
			<td>
		    <table WIDTH=100% BORDER=1>
		        <tr>
		            <th>Place</th>
		            <th>Points</th>
		            <th>Lines</th>
		            <th>Name</th>
		        </tr>
<?php

require_once('DB.php');

$dsn = array(
    'phptype' => 'mysql', 
    'username' => 'j178083rw',
    'password' => 'a1289b', 
    'hostspec' => 'mysql4-j.sourceforge.net',
    'database' => 'j178083_jetris'
);

$db =& DB::connect($dsn);
if(PEAR::isError($db)) {
    die($db->getMessage());
}

$sqlSelect = "SELECT * FROM hiscore ORDER BY hiscore DESC, hilines, id DESC";
$result =& $db->query($sqlSelect); 
if(PEAR::isError($result)){
    die ($db->getMessage());
}

$i = 1;
$idsToDel = array();

while($result->fetchInto($row,DB_FETCHMODE_ASSOC)) {
	
    if($i > 100) {
		$idsToDel[] = $row["id"];
	} else {
		echo "<tr>\n";
        echo "<td>$i</td> <td>".$row["hiscore"]."</td> <td>".$row["hilines"]."</td> <td>".base64_decode($row["name"])."</td>\n";
        echo "</tr>\n";
	}
    $i++;
    
}
echo("</table>\n");

//DELETE HiScore > 100;
if(count($idsToDel) > 0) {
	$sqlDel = "DELETE FROM hiscore WHERE ";
    foreach($idsToDel as $id) {
            $sqlDel = $sqlDel."id = $id OR ";
    }
    $sqlDel = substr($sqlDel, 0, strlen($sqlDel)-4);

    $result =& $db->query($sqlDel); 
    
    if(PEAR::isError($result)){
        die ($db->getMessage());
    }
}

$db->disconnect();

?>
    
	
	</td>
		</tr>
	</table>
	<div class="rule"></div>
	
<?php
include "templateDown.php";
?>
