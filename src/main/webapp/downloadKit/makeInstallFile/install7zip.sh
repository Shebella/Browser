#!/bin/sh
7za >>/dev/null  2>&1 
if [[ ! "$?" -eq 0 ]];then
  yum -y install p7zip;
fi
exit $?
