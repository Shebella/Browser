package tw.org.itri.ccma.css.safebox.service;

import tw.org.itri.ccma.css.safebox.model.Member;

/***
 * 前端會員登入服務
 * 
 * @author A10138
 * 
 */
public abstract class AbsLoginService implements ILoginService {

	/**
	 * 執行登入檢查動作, 若登入成功將取得完整 Member 資訊
	 * 
	 * @param user_id
	 * @param password
	 * @return
	 */
	public Member doLogin(String user_id, String password) {
		Member loginMember = login(user_id, password);
		if (null != loginMember) {
			afterLogin(loginMember);
		}

		return loginMember;
	}
}
