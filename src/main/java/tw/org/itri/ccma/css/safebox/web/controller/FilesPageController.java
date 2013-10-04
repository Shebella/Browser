package tw.org.itri.ccma.css.safebox.web.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import tw.org.itri.ccma.css.safebox.model.Bucket;
import tw.org.itri.ccma.css.safebox.model.ObjcetListBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBeanMsgInfo;
import tw.org.itri.ccma.css.safebox.model.PagingInfo;
import tw.org.itri.ccma.css.safebox.model.walrus.EventLog.ACTION_CODE;
import tw.org.itri.ccma.css.safebox.service.AbsDataService;

/***
 * Files � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
public class FilesPageController extends AbsPagesController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(FilesPageController.class);

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, Model model) {
		String bucketName = request.getParameter("bucket");
		String folderStart = request.getParameter("folder");
		String searchKeyword = request.getParameter("searchKeyword");

		/**
		 * �虜 client �府�賣�撣嗉��亦���bucket �
		 */
		if (StringUtils.isNotEmpty(bucketName)) {
			model.addAttribute("bucketName", bucketName);
			if (StringUtils.isNotEmpty(folderStart) && !"/".equals(folderStart)) {

				if (null == getDataService().getObjectMeta(bucketName, folderStart)) {
					model.addAttribute("msgInfo", "The folder " + folderStart + " does not exist.");
					return getViewPagePrefix() + "files";
				}

				// � folder �航�箏�撅� ��誑閬�隞�/"��敺�蝯�view 撅文�箸�
				String[] folderArr = folderStart.split("/");
				if (0 < folderArr.length) {
					List<String> folderList = Arrays.asList(folderArr).subList(0, folderArr.length - 1);
					if (0 < folderList.size()) {
						if (StringUtils.isNotEmpty(folderList.get(0))) {
							model.addAttribute("folderList", folderList);
						} else {
							model.addAttribute("folderList", folderList.subList(1, folderList.size()));
						}
					}
					model.addAttribute("folderEnd", folderArr[folderArr.length - 1]);
				}
			} else {
				folderStart = "/";
			}
			model.addAttribute("folderStart", folderStart);

			if (StringUtils.isNotEmpty(searchKeyword)) {
				model.addAttribute("searchKeyword", HtmlUtils.htmlEscape(searchKeyword));
			}

			String msgInfo = (String) request.getSession().getAttribute("msgInfo");
			if (StringUtils.isNotEmpty(msgInfo)) {
				model.addAttribute("msgInfo", msgInfo);
				request.getSession().removeAttribute("msgInfo");
			}

			return getViewPagePrefix() + "files";
		} else {
			/**
			 * 隞�”雿輻��湔暺�view 撅斤� Files �����撣嗡遙雿��� 撠��喲�閮�bucket ��Files �
			 */
			List<Bucket> bucketList = getDataService().getAllBuckets();
			if (0 < bucketList.size()) {
				bucketName = bucketList.get(0).getBucketName();
				return "redirect:/" + getViewPagePrefix() + "files?bucket=" + bucketName;
			} else {
				return "redirect:/" + getViewPagePrefix() + "account";
			}
		}
	}

	@RequestMapping(value = "/json/allfiles/{bucketName}/**", method = RequestMethod.GET)
	@ResponseBody
	/***
	 * Mobile Client 撠閬����objects
	 * @param bucket_name
	 * @param request
	 * @return
	 */
	public List<ObjectBean> getAllObjectList(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request) {

		List<ObjectBean> resultBeanList = getDataService().getObjectsByBucket(bucket_name, "/", "/", true);
		if (null != resultBeanList && 0 < resultBeanList.size()) {
			Collections.sort(resultBeanList);
		}

		return resultBeanList;
	}

	@RequestMapping(value = "/json/filesMobile/{bucketName}/**", method = RequestMethod.GET)
	@ResponseBody
	/***
	 * Mobile Client 撠閬���摰�bucket ��objects 皜銝虫��瑕����
	 * @param bucket_name
	 * @param request
	 * @return
	 */
	public ObjcetListBean showObjectListForMobile(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request) {

		String reqCurrentPage = request.getParameter("curPage");
		String reqPageSize = request.getParameter("pageSize");

		List<ObjectBean> resultBeanList = showObjectList(bucket_name, request);

		PagingInfo pagingInfo = PagingInfo.getInstance(resultBeanList.size(), getDefaultPageSize());
		pagingInfo.calculatePaging(reqCurrentPage, reqPageSize);

		return ObjcetListBean.getInstance(pagingInfo,
			   resultBeanList.subList(pagingInfo.getPageStart(), pagingInfo.getPageEnd()));
	}

	public List<ObjectBean> showObjectList(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request) {

		String searchKeyword = request.getParameter("searchKeyword");

		String objKey = "";
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());

			if (StringUtils.isNotEmpty(objKey) && !objKey.endsWith("/")) {
				objKey = objKey + "/";
			}

			if (StringUtils.isNotEmpty(searchKeyword)) {
				objKey = objKey + searchKeyword;
			}
		}

		List<ObjectBean> resultBeanList = getDataService().getObjectsByBucket(bucket_name, objKey, "/", false);

		if (null != resultBeanList && 0 < resultBeanList.size()) {
			Collections.sort(resultBeanList);
		}
		
		return resultBeanList;
	}
	
	@RequestMapping(value = "/json/files/{bucketName}/**", method = RequestMethod.GET)
	@ResponseBody
	/***
	 * �垢 AJAX 撠���� bucket ��objects 皜
	 * @param bucket_name
	 * @param request
	 * @return
	 */
	public List<ObjectBean> showObjectListForBrowser(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request) {

		String searchKeyword = request.getParameter("searchKeyword");

		String objKey = "";
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());

			if (StringUtils.isNotEmpty(objKey) && !objKey.endsWith("/")) {
				objKey = objKey + "/";
			}

			if (StringUtils.isNotEmpty(searchKeyword)) {
				objKey = objKey + searchKeyword;
			}
		}

		//System.out.println(System.currentTimeMillis());
		List<ObjectBean> resultBeanList = getDataService().getObjectsByBucket(bucket_name, objKey, "/", false);
		//System.out.println(System.currentTimeMillis());
		
		String reqCurrentPage = request.getParameter("curPage");
		String reqPageSize = request.getParameter("pageSize");
		
		PagingInfo pagingInfo = PagingInfo.getInstance(resultBeanList.size(), getDefaultPageSize());
		pagingInfo.calculatePaging(reqCurrentPage, reqPageSize);
		
		if (null != resultBeanList && 0 < resultBeanList.size()) {
			Collections.sort(resultBeanList);
		}

		return resultBeanList.subList(pagingInfo.getPageStart(), pagingInfo.getPageEnd());
	}

	@RequestMapping(value = "/json/put/{putType}/{bucketName}/**", method = { RequestMethod.PUT,
			RequestMethod.POST })
	@ResponseBody
	/***
	 * 銝瑼��遣蝡�桅���
	 * @param put_type
	 * @param bucket_name
	 * @param request
	 * @return
	 */
	public ObjectBean putObject(@PathVariable("putType") String put_type,
			@PathVariable("bucketName") String bucket_name, HttpServletRequest request) {
		ObjectBean objBean = null;

		String objKey = "";
		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}

		if (StringUtils.isNotEmpty(put_type) && StringUtils.isNotEmpty(bucket_name)
				&& StringUtils.isNotEmpty(objKey)) {
			String actionContent = "";
			boolean actionStatus = false;

			AbsDataService dataService = getDataService();
			if ("dir".equals(put_type)) {
				if (dataService.isObjectExisted(bucket_name, objKey)) {
					actionContent = objKey + " is already existed";
				} else {
					objBean = dataService.putDirObject(bucket_name, objKey);
					actionContent = "You added the folder " + objKey + ".";
				}
			} else if ("obj".equals(put_type)) {
				if (dataService.isDirObjectExisted(bucket_name, objKey)) {
					actionContent = "The folder " + objKey + " is already existed";
				} else {
					objBean = getDataService().putObject(bucket_name, objKey, null);
					actionContent = "You added the file " + objKey + ".";
				}
			}

			if (null != objBean) {
				actionStatus = true;
			}

			getLogService().logEvent(ACTION_CODE.UPLOAD, actionContent, actionStatus);
		}

		return objBean;
	}

	@RequestMapping(value = "/json/{actionType}/{putType}/{srcObjName}/{targetObjName}/{bucketName}/**", method = {
			RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	/***
	 * 瑼�/�桅���rename ��move ��
	 * @param action_type
	 * @param put_type
	 * @param bucket_name
	 * @param src_obj_name
	 * @param target_obj_name
	 * @param request
	 * @return
	 */
	public ObjectBeanMsgInfo moveObject(@PathVariable("actionType") String action_type,
			@PathVariable("putType") String put_type, @PathVariable("bucketName") String bucket_name,
			@PathVariable("srcObjName") String src_obj_name,
			@PathVariable("targetObjName") String target_obj_name, HttpServletRequest request) {
		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();

		String objKey = "";
		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			restOfTheUrl = restOfTheUrl.substring(restOfTheUrl.indexOf(src_obj_name) + src_obj_name.length());
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}

		if (StringUtils.isNotEmpty(action_type) && StringUtils.isNotEmpty(put_type)
				&& StringUtils.isNotEmpty(src_obj_name) && StringUtils.isNotEmpty(target_obj_name)
				&& StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(objKey)) {

			String targetObjKey = objKey;
			String srcObjKey = StringUtils.replaceOnce(objKey, target_obj_name, "") + src_obj_name;

			String actionContent = "";
			boolean actionStatus = false;

			AbsDataService dataService = getDataService();
			if ("move".equals(action_type)) {
				targetObjKey = objKey + "/" + src_obj_name;
				actionContent = "You moved the file " + src_obj_name + " to " + target_obj_name + ".";

				if ("dir".equals(put_type)) {
					actionContent = "You moved the folder " + src_obj_name + " to " + target_obj_name + ".";
				}

				objBean = (ObjectBeanMsgInfo) dataService.moveObject(getMember().getUserId(), bucket_name,
						srcObjKey, targetObjKey);
			} else if ("rename".equals(action_type)) {
				if (dataService.isObjectExisted(bucket_name, objKey)) {
					actionContent = src_obj_name + " can't rename to existed " + target_obj_name + ".";
				} else {
					objBean = (ObjectBeanMsgInfo) dataService.moveObject(getMember().getUserId(),
							bucket_name, srcObjKey, targetObjKey);

					if ("dir".equals(put_type)) {
						actionContent = "You renamed the folder " + src_obj_name + " to " + target_obj_name
								+ ".";
					} else {
						actionContent = "You renamed the file " + src_obj_name + " to " + target_obj_name
								+ ".";
					}
				}
			}

			if (objBean.isStatusCode()) {
				actionStatus = true;
			} else {
				objBean.setMsgInfo("move operation failed");
			}

			getLogService().logEvent(ACTION_CODE.MOVE, actionContent, actionStatus);
		}

		return objBean;
	}

	@RequestMapping(value = "/json/delete/{putType}/{srcObjName}/{bucketName}/**", method = {
			RequestMethod.DELETE, RequestMethod.POST })
	@ResponseBody
	/***
	 * �芷瑼�
	 * @param put_type
	 * @param bucket_name
	 * @param src_obj_name
	 * @param request
	 * @return
	 */
	public ObjectBeanMsgInfo deleteObject(@PathVariable("putType") String put_type,
			@PathVariable("bucketName") String bucket_name, @PathVariable("srcObjName") String src_obj_name,
			HttpServletRequest request) {
		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();

		String objKey = "";
		String restOfTheUrl = request.getPathInfo();
		if (StringUtils.isEmpty(restOfTheUrl)) {
			restOfTheUrl = (String) request
					.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		}
		restOfTheUrl = restOfTheUrl.substring(restOfTheUrl.indexOf(src_obj_name) + src_obj_name.length());
		objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());

		if (StringUtils.isNotEmpty(put_type) && StringUtils.isNotEmpty(src_obj_name)
				&& StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(objKey)) {
			boolean actionStatus = false;

			String putType = "file";
			if ("dir".equals(put_type)) {
				putType = "folder";
			}

			String actionContent = "You deleted the " + putType + " " + src_obj_name + ".";

			objBean = (ObjectBeanMsgInfo) getDataService().deleteObject(bucket_name, objKey);
			if (objBean.isStatusCode()) {
				actionStatus = true;
			} else {
				objBean.setMsgInfo("delete operation failed");
			}

			getLogService().logEvent(ACTION_CODE.UPLOAD, actionContent, actionStatus);
		}

		return objBean;
	}

	@RequestMapping(value = "/json/tree/{bucketName}", method = { RequestMethod.PUT, RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	/***
	 * 瑼�/�桅���move ��(蝘餃���folder)
	 * @param bucket_name
	 * @param request
	 * @param redirect_attr
	 * @return
	 */
	public ObjectBeanMsgInfo moveTreeObject(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request, RedirectAttributes redirect_attr) {

		String folderStart = request.getParameter("folderStart");
		String srcObjNames = request.getParameter("srcObjNames");
		String targetFolder = request.getParameter("targetFolder");

		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();
		if (StringUtils.isNotEmpty(folderStart) && StringUtils.isNotEmpty(srcObjNames)
				&& StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(targetFolder)) {

			if ("/".equals(folderStart)) {
				folderStart = "";
			}

			targetFolder = StringUtils.replace(targetFolder, getTreeRootKey(), "");
			if ("/".equals(targetFolder)) {
				targetFolder = "";
			}

			boolean actionStatus = true;
			String actionContent = "";
			String[] srcObjArr = srcObjNames.split(",");
			if (1 == srcObjArr.length) {
				actionContent = "You moved the file " + folderStart + "/" + srcObjArr[0] + " to "
						+ targetFolder + "/" + srcObjArr[0] + ".";
			} else {
				actionContent = "You moved the files to " + targetFolder + "/" + srcObjArr[0] + ".";
			}

			for (int i = 0; i < srcObjArr.length; i++) {
				if (StringUtils.isNotEmpty(srcObjArr[i])) {
					String srcObjKey = folderStart + "/" + srcObjArr[i];
					String targetObjKey = targetFolder + "/" + srcObjArr[i];

					if (!srcObjKey.equals(targetFolder) && !srcObjKey.equals(targetObjKey)) {
						objBean = (ObjectBeanMsgInfo) getDataService().moveObject(getMember().getUserId(),
								bucket_name, srcObjKey, targetObjKey);

						if (!objBean.isStatusCode()) {
							actionStatus = false;
							objBean.setMsgInfo("move operation failed");
							break;
						}
					}
				}
			}

			getLogService().logEvent(ACTION_CODE.UPLOAD, actionContent, actionStatus);
		}

		return objBean;
	}
}
