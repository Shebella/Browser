package tw.org.itri.ccma.css.safebox.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.model.walrus.AuthUser;
import tw.org.itri.ccma.css.safebox.repository.WalrusAuthUserRepository;

/***
 * 身份驗證服務
 * 
 * @author A10138
 * 
 */
@Service
public class AuthUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthUserService.class);

	@Autowired
	private WalrusAuthUserRepository authUserRepository;

	/***
	 * 透過 Walrus Database 取得指定的使用者資訊
	 * 
	 * @param access_key
	 * @param secret_key
	 * @return
	 */
	@Transactional(readOnly = true)
	public AuthUser getAuthUser(String access_key, String secret_key) {
		if (StringUtils.isNotEmpty(access_key) && StringUtils.isNotEmpty(secret_key)) {
			return authUserRepository.findByAccessKeyAndSecretKey(access_key, secret_key);
		}

		return null;
	}

	/***
	 * 傳入使用者的 AccessKey 與 SecretKey 取得登入的 Member 資訊
	 * 
	 * @param access_key
	 * @param secret_key
	 * @return
	 */
	public Member getMember(String access_key, String secret_key) {
		AuthUser authUser = getAuthUser(access_key, secret_key);
		if (null != authUser) {
			Member member = new Member();
			member.setUserId(authUser.getUserName());
			member.setUserName(authUser.getUserName());
			member.setAccessKey(authUser.getAccessKey());
			member.setSecretKey(authUser.getSecretKey());

			return member;
		}

		return null;
	}

	/***
	 * 驗證指定的 AccessKey 與 SecretKey 是否為有效的使用者
	 * 
	 * @param access_key
	 * @param secret_key
	 * @return
	 */
	public boolean isValidUser(String access_key, String secret_key) {
		if (null == getAuthUser(access_key, secret_key)) {
			LOGGER.warn("=== invalid user by 2 keys: " + access_key + ", " + secret_key);

			return false;
		}

		return true;
	}
}
