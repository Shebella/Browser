#/!bin/sh
HOST=`/sbin/ifconfig | grep "inet addr" | awk -F: '{print $2}' | awk '{print $1}'|head -n 1`
DOWNLOAD_HOST=10.218.10.1
DOWNLOAD_PATH='/safebox/branches/alpha_1_0/'
WIN_FILE="Safebox_setup_win.exe"
LINUX_FILE="Safebox_setup_linux.bin"
VERSION_FILE="version.txt"


usage(){
	echo "Usage:"
	echo "-h \$HOST"
	echo "-r \$DOWNLOAD_HOST"
	echo "-p \$DOWNLOAD_PATH"
	echo "-rev \$svn_number \$build_number"
}


while [ $# -ne 0 ] ; do
	case $1 in 
	-help)
            usage
            exit
	    shift;;
        -h) HOST=$2;
	    shift;;
	-r) DOWNLOAD_HOST=$2;
	    shift;;
	-p) DOWNLOAD_PATH=$2
	    shift;;
	-rev) 
	    WIN_FILE="SafeboxSetupWindows_$2_$3.exe"
            LINUX_FILE="SafeboxSetupLinux_$2_$3.bin"
            VERSION_FILE="SafeboxSetupWindows_$2_$3_version.txt"
	shift;shift;;


	esac
	shift;
done




WINFILE="SafeboxAutoExtract.exe"
UNIXFILE="SafeboxAutoExtract.sh"

TMPFILE="safebox.7z Safebox.exe Safebox.bin Safebox.tgz"

sh install7zip.sh
if [[ ! "$?" -eq "0" ]];then echo "can not install and run 7-zip.";exit 1;fi
sh getInstaller.sh -r $DOWNLOAD_HOST -p $DOWNLOAD_PATH -wf $WIN_FILE -lf $LINUX_FILE -vf $VERSION_FILE
if [[ ! "$?" -eq "0" ]];then echo "can not download installer.";exit 1;fi

SHOWMSG="(paked date:`date`,\ngot paked installer from:$DOWNLOAD_HOST\nCSS server IP:$HOST)"
7za a -y safebox.7z Safebox.exe
cat 7zS.sfx >$WINFILE
sed s/%IP%/$HOST/g win.config |sed s/%MSG%/"$SHOWMSG"/g >>$WINFILE
cat safebox.7z >>$WINFILE
#make signed windows self-extract file
cd ../makeSignedFile
sh makeSignedFile.sh ../makeInstallFile/$WINFILE ../makeInstallFile/mysign.exe
mv -f ../makeInstallFile/mysign.exe ../makeInstallFile/$WINFILE
cd ../makeInstallFile

tar -czf Safebox.tgz Safebox.bin
sed s/%IP%/$HOST/g unix.config|sed s/%MSG/"$SHOWMSG"/g >$UNIXFILE
cat Safebox.tgz >>$UNIXFILE
#sign linux self-extract file in this line.

mv -f $WINFILE ../
mv -f $UNIXFILE ../
mv -f version.txt ../

chmod 777 ../$WINFILE
chmod 777 ../$UNIXFILE


rm -rf $TMPFILE
