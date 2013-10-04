package tw.org.itri.ccma.css.safebox.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * API � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class ApiPageController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(ApiPageController.class);

	@RequestMapping(value = "/apiVersion", method = RequestMethod.GET)
	public String showPage(Model model) {
		return getViewPagePrefix() + "apiVersion";
	}
}
