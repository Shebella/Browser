package tw.org.itri.ccma.css.safebox.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import tw.org.itri.ccma.css.safebox.config.AppConfig;
import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.service.WalrusDataService;

/***
 * �垢�餃敺� Bucket 甈�瑼Ｘ Interceptor
 * 
 * @author Keanu
 * 
 */
public class BucketAclCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(BucketAclCheckInterceptor.class);

	@Autowired
	private AppConfig appConfig;

	/*@Autowired
	private ClientConfig clientConfig;*/

	@Autowired
	private WalrusDataService dataService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String bucketName = request.getParameter("bucket");
		Member loginMember = (Member) request.getAttribute(appConfig.getMemberSessionName());

		if (StringUtils.isNotEmpty(bucketName) && null != loginMember) {

			dataService.setUserAccessKey(loginMember.getAccessKey());
			dataService.setUserSecretKey(loginMember.getSecretKey());
			if (null == dataService.getBucket(bucketName)) {
				LOGGER.warn("=== redirect to default bucket: ");
				throw new ModelAndViewDefiningException(new ModelAndView("redirect:/pages/files?bucket="
						+ appConfig.getDefaultBucketName(loginMember.getUserName())));
			}

			dataService.shutdownService();
		}

		return super.preHandle(request, response, handler);

	}
}
