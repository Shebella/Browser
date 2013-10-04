package tw.org.itri.ccma.css.safebox.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tw.org.itri.ccma.css.safebox.config.AppConfig;
import tw.org.itri.ccma.css.safebox.config.ClientConfig;
import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.service.AuthUserService;

/***
 * 前端登入檢查用的 Interceptor
 * 
 * @author Keanu
 * 
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	private List<String> ignoredList;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ClientConfig clientConfig;

	@Autowired
	private AuthUserService authUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean checkFlag = true;

		if (null != ignoredList) {
			for (String checkUrl : ignoredList) {
				if (request.getRequestURI().contains(checkUrl)) {
					checkFlag = false;
					break;
				}
			}
		}

		if (checkFlag) {
			Member loginMember = (Member) request.getSession().getAttribute(appConfig.getMemberSessionName());
			if (null == loginMember) {
				/**
				 * 代表有可能是從 Mobile Client 過來的，檢查 HEADER 資訊
				 */
				loginMember = authUserService.getMember(
						request.getHeader(clientConfig.getClientAccessKeyCode()),
						request.getHeader(clientConfig.getClientSecretKeyCode()));
			}

			if (null == loginMember) {
				throw new ModelAndViewDefiningException(new ModelAndView("redirect:/"));
			} else {
				request.setAttribute(appConfig.getMemberSessionName(), loginMember);
			}
		}

		return super.preHandle(request, response, handler);
	}

	public List<String> getIgnoredList() {
		return ignoredList;
	}

	public void setIgnoredList(List<String> ignoredList) {
		this.ignoredList = ignoredList;
	}
}
