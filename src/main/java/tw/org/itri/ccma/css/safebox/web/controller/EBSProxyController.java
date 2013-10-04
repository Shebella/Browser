package tw.org.itri.ccma.css.safebox.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tw.org.itri.ccma.css.safebox.model.client.BucketInfoResult;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService;

/***
 * 蝯�Desktop Client 撠��EBS Service 隞���Controller
 * 
 * @author Keanu
 * 
 */
@Controller
@RequestMapping("/ebs")
public class EBSProxyController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(EBSProxyController.class);

	@Autowired
	IEventSyncService ebsService;

	@RequestMapping(value = "/queryBucketInfo/{accountMember}/{bucketName}", method = RequestMethod.GET)
	public String showPage(@PathVariable("bucketName") String bucket_name,
			@PathVariable("accountMember") String account_member, Model model) {

		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(account_member)) {
			BucketInfoResult bucketResult = ebsService.performBucketInfoQuery(account_member, bucket_name);

			if (null != bucketResult) {
				model.addAttribute("resultInfo", bucketResult.getObjectCount());
			} else {
				model.addAttribute("resultInfo", 0);
			}
		}

		return "ebsInfo";
	}
}
