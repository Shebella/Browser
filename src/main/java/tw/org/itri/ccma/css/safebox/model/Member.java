package tw.org.itri.ccma.css.safebox.model;



/***
 * 嚙踝��ｇ蕭擗�綽蕭Member �嚙�
 * 
 * @author A10138
 * 
 */
public class Member {
	private static Member instance = new Member();
	
	private String userId;
	private String accessKey;
	private String secretKey;
	private String instanceKey;
	private String userName;
	private String syncID;
	private String clientTypeForEBS;
	
	public static Member getInstance() {
		return instance;
	}
	
	public String getInstanceKey() {
		return instanceKey;
	}

	public void setInstanceKey(String instanceKey) {
		this.instanceKey = instanceKey;
	}
	
	public String getSyncID() {
		return syncID;
	}

	public void setSyncID(String syncID) {
		this.syncID = syncID;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientTypeForEBS() {
		return clientTypeForEBS;
	}

	public void setClientTypeForEBS(String clientTypeForEBS) {
		this.clientTypeForEBS = clientTypeForEBS;
	}
}
