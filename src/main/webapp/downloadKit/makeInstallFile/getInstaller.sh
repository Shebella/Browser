#/bin/sh
DOWNLOAD_HOST=10.218.10.1
DOWNLOAD_PATH='/safebox/branches/alpha_1_0/'

WIN_FILE="Safebox_setup_win.exe"
LINUX_FILE="Safebox_setup_linux.bin"
VERSION_FILE="version.txt"

usage(){
   echo "Usage:"

   echo -help;
   echo "print help meassge."
   echo 

   echo -r \$download_host
   echo "host at which we download the file."
   echo 

   echo -p \$download_path
   echo "path at which we download the file."  
   echo 

   echo -wf \$win_file
   echo "file name of the windows installer."
   echo 

   echo -lf \$win_file
   echo "file name of the linux installer."
   echo 

   echo -vf \$win_file
   echo "file name of the version files."
   echo 

}



while [ $# -ne 0 ] ; do
        case $1 in
	-help)
	    usage;
	    exit 1
	    shift;;
        -r) DOWNLOAD_HOST=$2;
            shift;;
        -p) DOWNLOAD_PATH=$2;
            shift;;
	-wf)
            WIN_FILE=$2;
	    shift;;
	-lf)
            LINUX_FILE=$2;
            shift;;
	-vf) 
            VERSION_FILE=$2
	    shift;;
        esac
        shift;
done
if [[ ! "$?" -eq "0" ]];then exit 1;fi

wget -t 1 -T 20 -O Safebox.exe http://$DOWNLOAD_HOST/$DOWNLOAD_PATH/$WIN_FILE
if [[ ! "$?" -eq "0" ]];then exit 1;fi

wget -t 1 -T 20 -O Safebox.bin http://$DOWNLOAD_HOST/$DOWNLOAD_PATH/$LINUX_FILE
if [[ ! "$?" -eq "0" ]];then exit 1;fi

wget -t 1 -T 20 -O version.txt http://$DOWNLOAD_HOST/$DOWNLOAD_PATH/$VERSION_FILE
if [[ ! "$?" -eq "0" ]];then exit 1;fi
