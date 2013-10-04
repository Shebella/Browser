keytool -genkey -storepass password -keyalg DSA -alias SafeboxDownloadManager -dname "CN=itri.com, OU=CCMA, O=test_applet, L=a, ST=b, C=CA, EMAILADDRESS=roedyg@mindprod.com DC=mindprod, DC=com" -validity 999  

keytool -selfcert  -storepass password -alias SafeboxDownloadManager  -validity 999  

keytool -exportcert  -storepass password -alias SafeboxDownloadManager -rfc -file SafeboxDownloadManager.cer