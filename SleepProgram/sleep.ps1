param($Argument1)
$secs = $Argument1
$minutes= 60*$secs;
Start-sleep -s $minutes

Add-Type -Assembly System.Windows.Forms
[System.Windows.Forms.Application]::SetSuspendState("Suspend", $false, $true)