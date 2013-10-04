package tw.org.itri.ccma.css.safebox.model.walrus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * Walrus 的 AuthUser 資訊
 * 
 * @author A10138
 * 
 */
@Entity
@Table(name = "auth_users", schema = "public")
public class AuthUser {
	@Id
	@Column(name = "id")
	private String userId;

	@Column(name = "last_update_timestamp")
	private Date lastUpdateTimeStamp;

	@Column(name = "version")
	private int version;

	@Column(name = "auth_user_is_admin")
	private boolean isAdmin;

	@Column(name = "auth_user_is_enabled")
	private boolean isEnabled;

	@Column(name = "auth_user_name")
	private String userName;

	@Column(name = "auth_user_password")
	private String password;

	@Column(name = "auth_user_query_id")
	private String accessKey;

	@Column(name = "auth_user_secretkey")
	private String secretKey;

	@Column(name = "auth_user_token")
	private String token;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLastUpdateTimeStamp() {
		return lastUpdateTimeStamp;
	}

	public void setLastUpdateTimeStamp(Date lastUpdateTimeStamp) {
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
