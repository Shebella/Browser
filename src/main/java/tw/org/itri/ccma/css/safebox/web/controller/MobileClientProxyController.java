package tw.org.itri.ccma.css.safebox.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBeanMsgInfo;
import tw.org.itri.ccma.css.safebox.model.client.QueryBean;
import tw.org.itri.ccma.css.safebox.model.walrus.WalrusObject;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService;
import tw.org.itri.ccma.css.safebox.util.ObjectBeanUtil;

/***
 * Mobile Client �澆撠��API Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class MobileClientProxyController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(MobileClientProxyController.class);

	@Autowired
	IEventSyncService ebsService;

	/***
	 * �亥岷憭���objects 鞈�
	 * 
	 * @param bucket_name
	 * @param query_bean_arr
	 * @return
	 */
	@RequestMapping(value = "/json/multiquery/{bucketName}", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public List<ObjectBean> getObjectBeansInfo(@PathVariable("bucketName") String bucket_name,
			@RequestBody QueryBean[] query_bean_arr) {
		List<ObjectBean> resultBeanList = new ArrayList<ObjectBean>();

		if (null != query_bean_arr && 0 < query_bean_arr.length) {
			List<String> objKeyList = new ArrayList<String>();
			for (QueryBean queryBean : query_bean_arr) {
				objKeyList.add(ObjectBeanUtil.urlEncode(queryBean.getObjectKey()));
			}

			List<WalrusObject> objList = ebsService.getWalrusObjectList(bucket_name, objKeyList);

			for (QueryBean queryBean : query_bean_arr) {
				ObjectBeanMsgInfo objBean = ObjectBeanUtil.genObjectBean(queryBean.getObjectKey(),
						queryBean.getEtagId());
				objBean.setMsgInfo("file is not existed");

				// added by keanu 20130108, for mobile client, need
				// the object name
				String tempName = objBean.getObjectId();
				if (tempName.endsWith("/")) {
					tempName = tempName.substring(0, tempName.length() - 1);
				}
				int idxSlash = tempName.lastIndexOf("/");
				if (-1 < idxSlash) {
					tempName = tempName.substring(idxSlash + 1, tempName.length());
				}

				objBean.setObjectName(ObjectBeanUtil.urlDecode(tempName));

				for (WalrusObject walrusObj : objList) {
					if (walrusObj.getObjectKey().equals(ObjectBeanUtil.urlEncode(objBean.getObjectId()))) {
						objBean.setBucketName(walrusObj.getBucketName());
						objBean.setContentType(walrusObj.getContentType());
						objBean.setObjectUUID(walrusObj.getObjectName());
						objBean.setObjectId(ObjectBeanUtil.urlDecode(objBean.getObjectId()));

						if (walrusObj.getEtag().equals(objBean.getEtagId())) {
							objBean.setStatusCode(true);
							objBean.setMsgInfo("");
						} else {
							objBean.setMsgInfo("file was modified");
						}

						objBean.setEtagId(walrusObj.getEtag());

						break;
					}
				}

				resultBeanList.add(objBean);
			}
		}

		return resultBeanList;
	}

	/***
	 * �亥岷�桃� Object 鞈�
	 */
	@RequestMapping(value = "/json/query/{bucketName}/**", method = RequestMethod.GET)
	@ResponseBody
	public ObjectBean getObjectBeanInfo(@PathVariable("bucketName") String bucket_name,
			@RequestParam("etag") String object_etag, HttpServletRequest request) {
		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();

		String objKey = "";
		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}

		if (StringUtils.isNotEmpty(objKey)) {
			objBean = getDataService().getObjectBean(bucket_name, objKey);
			if (objBean.isStatusCode()) {
				objBean.setObjectUUID(ebsService.getUUIDByObject(objBean.getBucketName(),
						ObjectBeanUtil.urlEncode(objBean.getObjectId())));

				if (StringUtils.isNotEmpty(object_etag) && !object_etag.equals(objBean.getEtagId())) {
					objBean.setStatusCode(false);
					objBean.setMsgInfo("file was modified");
				}
			} else {
				objBean.setMsgInfo("file is not existed");
			}
		}

		return objBean;
	}

}
