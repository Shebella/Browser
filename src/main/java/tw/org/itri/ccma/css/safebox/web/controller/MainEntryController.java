package tw.org.itri.ccma.css.safebox.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tw.org.itri.ccma.css.safebox.config.AccountConfig;
import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.service.AbsLoginService;
import tw.org.itri.ccma.css.safebox.web.form.AuthUserForm;

/***
 * 擐� Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class MainEntryController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(MainEntryController.class);

	@Autowired
	@Qualifier("itriLoginAdapter")
	AbsLoginService accountServiceAdapter;

	@Autowired
	AccountConfig accountConfig;

	private void updatePageAttributes(Model model) {
		model.addAttribute("safeboxSiteLink", accountConfig.getSafeboxSiteLink());
		model.addAttribute("downloadLink", accountConfig.getDownloadLink());
		model.addAttribute("downloadLinkWindows", accountConfig.getDownloadLinkWindows());
		model.addAttribute("downloadLinkLinux", accountConfig.getDownloadLinkLinux());
		model.addAttribute("faqLink", accountConfig.getFaqLink());
	}

	@RequestMapping(value = "/")
	public String home(HttpServletRequest request, Model model) {
		if (null != request.getSession().getAttribute("member")) {
			return "redirect:" + AbsPagesController.VIEW_PAGE_PREFIX + "account";
		}

		updatePageAttributes(model);
		return "home";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();

		model.addAttribute("msgInfo", "logout successfully");

		updatePageAttributes(model);
		return "home";
	}

	@RequestMapping(value = "/timeout")
	public String timeout(Model model) {
		model.addAttribute("msgInfo", "Your session has timed out, please login again");

		updatePageAttributes(model);
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String memberLogin(@ModelAttribute("authUser") AuthUserForm login_form, HttpSession session,
			Model model) {
		if (null != login_form && StringUtils.isNotEmpty(login_form.getUserId())
				&& StringUtils.isNotEmpty(login_form.getPassword())) {

			Member loginMember = accountServiceAdapter.doLogin(login_form.getUserId().toLowerCase(),
					login_form.getPassword());

			if (null != loginMember) {
				session.setAttribute("member", loginMember);
				return "redirect:" + AbsPagesController.VIEW_PAGE_PREFIX + "files";
			}
		}

		model.addAttribute("msgInfo", "login failed");

		updatePageAttributes(model);
		return "home";
	}
}
