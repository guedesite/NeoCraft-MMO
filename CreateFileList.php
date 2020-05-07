<?php

$rii = new RecursiveIteratorIterator(new RecursiveDirectoryIterator("file/"));


    foreach ($rii as $file)
	{
	
        if (!$file->isDir())
		{
            echo str_replace("file/","",$file->getPathname())."&".md5_file($file->getPathname())."\r\n";
		}
	}
