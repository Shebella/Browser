1.by the configuration as follow, this folder wouldn't be access by web user.

<security-constraint>
    		<web-resource-collection>
        		<web-resource-name>Include files</web-resource-name>
        		<description>No direct access to include files.</description>
        		<url-pattern>/downloadKit/makeSignedJar/*</url-pattern>
        		<http-method>POST</http-method>
        		<http-method>GET</http-method>
    		</web-resource-collection>
    	<auth-constraint>
        	<description>No direct browser access to include files.</description>
        	<role-name>NobodyHasThisRole</role-name>
    	</auth-constraint>

2.when using sign_jar.bat,type the password 'password'.


