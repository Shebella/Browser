package tw.org.itri.ccma.css.safebox.web.form;

/***
 * 前端首頁登入用的身份驗證 Form
 * 
 * @author Keanu
 * 
 */
public class AuthUserForm {

	private String userId;
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
