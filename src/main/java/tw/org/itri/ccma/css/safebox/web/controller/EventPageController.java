package tw.org.itri.ccma.css.safebox.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.org.itri.ccma.css.safebox.model.walrus.EventLog;

/***
 * Event � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class EventPageController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(EventPageController.class);

	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String showPage(Model model) {
		return getViewPagePrefix() + "event";
	}

	@RequestMapping(value = "/json/event/{logDate}", method = RequestMethod.GET)
	@ResponseBody
	public List<EventLog> showEventLogList(@PathVariable("logDate") String log_date) {
		return getLogService().getLogsByMember(log_date);
	}

	@RequestMapping(value = "/json/event", method = RequestMethod.GET)
	@ResponseBody
	public List<EventLog> showEventLogList() {
		return getLogService().getLogsByMember(null);
	}
}
