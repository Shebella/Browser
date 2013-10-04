package tw.org.itri.ccma.css.safebox.config;

/***
 * Mobile/Browser app 等 client 相關共用設定
 * 
 * @author A10138
 * 
 */
public class ClientConfig {
	private String serviceURL;
	private String resourceName;
	private String clientTypeForEBS;
	private String clientAccessKeyCode;
	private String clientSecretKeyCode;
	private String clientTypeForEBSCode;

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getClientTypeForEBS() {
		return clientTypeForEBS;
	}

	public void setClientTypeForEBS(String clientTypeForEBS) {
		this.clientTypeForEBS = clientTypeForEBS;
	}

	public String getClientAccessKeyCode() {
		return clientAccessKeyCode;
	}

	public void setClientAccessKeyCode(String clientAccessKeyCode) {
		this.clientAccessKeyCode = clientAccessKeyCode;
	}

	public String getClientSecretKeyCode() {
		return clientSecretKeyCode;
	}

	public void setClientSecretKeyCode(String clientSecretKeyCode) {
		this.clientSecretKeyCode = clientSecretKeyCode;
	}

	public String getClientTypeForEBSCode() {
		return clientTypeForEBSCode;
	}

	public void setClientTypeForEBSCode(String clientTypeForEBSCode) {
		this.clientTypeForEBSCode = clientTypeForEBSCode;
	}
}
