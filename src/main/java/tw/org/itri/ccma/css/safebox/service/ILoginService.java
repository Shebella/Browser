package tw.org.itri.ccma.css.safebox.service;

import tw.org.itri.ccma.css.safebox.model.Member;

public interface ILoginService {

	public abstract Member login(String user_id, String password);

	public abstract void afterLogin(Member member);

}
