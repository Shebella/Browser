package tw.org.itri.ccma.css.safebox.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * Help � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class HelpPageController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(HelpPageController.class);

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String showPage(Model model) {
		return getViewPagePrefix() + "help";
	}
}
