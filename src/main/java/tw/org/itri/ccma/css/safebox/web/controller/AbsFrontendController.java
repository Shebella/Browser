package tw.org.itri.ccma.css.safebox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tw.org.itri.ccma.css.safebox.config.AppConfig;
import tw.org.itri.ccma.css.safebox.config.ClientConfig;
import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.service.AbsDataService;
import tw.org.itri.ccma.css.safebox.service.AuthUserService;
import tw.org.itri.ccma.css.safebox.service.EventLoggingService;

/***
 * 前端用基底 Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public abstract class AbsFrontendController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbsFrontendController.class);

	/**
	 * 操作 move 動作時呈現 folder tree 時所預設 root 代碼
	 */
	private static final String TREE_ROOT_KEY = "/$ROOT";

	/***
	 * 預設頁面最大條列筆數
	 */
	private static final int PAGE_SIZE = 50;

	public static String getTreeRootKey() {
		return TREE_ROOT_KEY;
	}

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ClientConfig clientConfig;

	@Autowired
	@Qualifier("walrusDataService")
	private AbsDataService dataService;

	@Autowired
	private EventLoggingService logService;

	@Autowired
	private AuthUserService authUserService;

	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public Member getMember() {
		/**
		 * 先嘗試從 SESSION 中取得登入後的 Member 資訊
		 */
		Member loginMember = (Member) RequestContextHolder.currentRequestAttributes().getAttribute(
				appConfig.getMemberSessionName(), RequestAttributes.SCOPE_SESSION);

		if (null == loginMember) {
			/**
			 * 如果沒有代表 request 可能從 mobile client 過來的，則檢查 request 的 HEADER 資訊
			 */
			HttpServletRequest httpReq = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();

			loginMember = authUserService.getMember(httpReq.getHeader(clientConfig.getClientAccessKeyCode()),
					httpReq.getHeader(clientConfig.getClientSecretKeyCode()));

			if (null != loginMember) {
				/**
				 * 若確認為 mobile client 過來的 client 則幫忙把 clientType 資訊串進 Member
				 * 物件中，以便後面的操作可以直接參考
				 */
				loginMember.setClientTypeForEBS(httpReq.getHeader(clientConfig.getClientTypeForEBSCode()));
			}
		}

		/***
		 * 若不是 mobile client 登入的 client 則預期 clientType 資訊應為空，就當做是 Browser App
		 */
		if (StringUtils.isEmpty(loginMember.getClientTypeForEBS())) {
			loginMember.setClientTypeForEBS(clientConfig.getClientTypeForEBS());
		}

		return loginMember;
	}

	public int getDefaultPageSize() {
		return PAGE_SIZE;
	}

	public AbsDataService getDataService() {
		Member loginMember = getMember();
		if (null != loginMember) {
			/***
			 * 回傳 S3 Service 前要先幫忙塞好對應的 Member 資訊
			 */
			dataService.setUserAccessKey(loginMember.getAccessKey());
			dataService.setUserSecretKey(loginMember.getSecretKey());
			dataService.setClientTypeForEBS(loginMember.getClientTypeForEBS());

			return dataService;
		} else {
			LOGGER.error("=== can't get the login member, dataservice will be null");
		}

		return null;
	}

	public EventLoggingService getLogService() {
		Member loginMember = getMember();
		if (null != loginMember) {
			logService.setMemberInfo(loginMember);

			return logService;
		} else {
			LOGGER.error("=== can't get the login member, logservice will be null");
		}

		return null;
	}

}
