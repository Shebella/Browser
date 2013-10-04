package tw.org.itri.ccma.css.safebox.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 前端頁面呈現用 Controllers
 * 
 * @author Keanu
 * 
 */
@Controller
@RequestMapping("/pages")
public abstract class AbsPagesController extends AbsFrontendController {
	/**
	 * 前端頁面 routing 設定
	 */
	public final static String VIEW_PAGE_PREFIX = "pages/";

	public String getViewPagePrefix() {
		return VIEW_PAGE_PREFIX;
	}
}
