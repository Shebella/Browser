yum -y install osslsigncode >/dev/null
osslsigncode -spc "INDUSTRIAL TECHNOLOGY RESEARCH INSTITUTE.p7b" -key "INDUSTRIAL TECHNOLOGY RESEARCH INSTITUTE .der" -n "Safebox" -i www.itri.org.tw -in $1 -out $2
