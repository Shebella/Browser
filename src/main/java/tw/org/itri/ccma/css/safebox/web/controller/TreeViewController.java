package tw.org.itri.ccma.css.safebox.web.controller;

import java.util.ArrayList;
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

import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBean.OBJECT_TYPE;
import tw.org.itri.ccma.css.safebox.model.TreeViewNode;

/***
 * 瑼��� move 憿舐內 Folder Tree � Controller
 * 
 * @author Keanu
 * 
 */
@Controller
@RequestMapping(value = "/treeView")
public class TreeViewController extends AbsFrontendController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(TreeViewController.class);

	private String folderStart = "";
	private String srcObjects = "";
	private String[] srcObjArr;

	@RequestMapping(value = "/{bucketName}/**", method = RequestMethod.GET)
	public String showForm(@PathVariable("bucketName") String bucket_name, HttpServletRequest request,
			Model model) {
		model.addAttribute("bucketName", bucket_name);

		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			folderStart = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}

		String moveItemsInfo = "";
		List<String> srcObjList = new ArrayList<String>();
		srcObjects = request.getParameter("srcObjects");
		if (StringUtils.isNotEmpty(srcObjects)) {
			srcObjArr = srcObjects.split(",");

			if (1 == srcObjArr.length) {
				moveItemsInfo = "the file " + srcObjArr[0];
			} else {
				moveItemsInfo = "files";
			}

			for (int i = 0; i < srcObjArr.length; i++) {
				// srcObjList.add(folderStart + "/" + srcObjArr[i]);
				if (StringUtils.isNotEmpty(srcObjArr[i])) {
					srcObjList.add(srcObjArr[i]);
				}
			}

			model.addAttribute("srcObjList", srcObjList);
			model.addAttribute("srcObjects", srcObjects);
		}
		model.addAttribute("moveItemsInfo", moveItemsInfo);

		model.addAttribute("folderStart", folderStart);
		return AbsPagesController.VIEW_PAGE_PREFIX + "treeView";
	}

	/***
	 * 憿舐內 Tree Root ��
	 * 
	 * @param bucket_name
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json/{bucketName}", method = RequestMethod.GET)
	@ResponseBody
	public List<TreeViewNode> showTreeRoot(@PathVariable("bucketName") String bucket_name,
			HttpServletRequest request) {

		List<TreeViewNode> folderList = new ArrayList<TreeViewNode>();

		TreeViewNode treeNode = new TreeViewNode();
		treeNode.setKey(getTreeRootKey());
		treeNode.setTitle("/");
		treeNode.setLazy(false);
		treeNode.setFolder(true);
		treeNode.setExpand(true);
		folderList.add(treeNode);

		List<TreeViewNode> subNodeList = new ArrayList<TreeViewNode>();
		List<ObjectBean> objList = getDataService().getObjectsByBucket(bucket_name, "/", "/", false);
		for (ObjectBean objBean : objList) {
			if (OBJECT_TYPE.FOLDER.toString().equals(objBean.getType())) {
				TreeViewNode subNode = new TreeViewNode();
				subNode.setKey(objBean.getObjectId());
				subNode.setTitle(objBean.getObjectName());
				subNode.setLazy(true);
				subNode.setFolder(true);

				subNodeList.add(subNode);
			}
		}
		treeNode.setSubNodeList(subNodeList);

		return folderList;
	}

	/***
	 * 靘�摰� folder name(object key)�撠���tree list
	 */
	@RequestMapping(value = "/json/{bucketName}/**", method = RequestMethod.GET)
	@ResponseBody
	public List<TreeViewNode> showTreeList(@PathVariable("bucketName") String bucket_name, HttpServletRequest request) {

		List<TreeViewNode> folderList = new ArrayList<TreeViewNode>();

		String objKey = "";
		String objectKey = "";
		List<String> objKeyList = new ArrayList<String>();
		
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			objKey = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());

			if (StringUtils.isNotEmpty(objKey) && !objKey.endsWith("/")) {
				objKey = objKey + "/";
			} 
			
			else {
				TreeViewNode treeNode = new TreeViewNode();
				treeNode.setKey("/");
				treeNode.setTitle(bucket_name);
				treeNode.setLazy(true);
				treeNode.setFolder(true);

				folderList.add(treeNode);
			}
			
			for (int i = 0; i < srcObjArr.length; i++) {
				if (folderStart.endsWith("/")) {
					objectKey = folderStart + srcObjArr[i] + "/";
				}
				
				else {
					objectKey = folderStart + "/" + srcObjArr[i] + "/";
				}

				objKeyList.add(objectKey);
			}

			List<ObjectBean> objList = getDataService().getObjectsByBucket(bucket_name, objKey, "/", false);
			
			for (ObjectBean objBean : objList) {
				if (OBJECT_TYPE.FOLDER.toString().equals(objBean.getType()) && !objKey.equals(objectKey)) {
					TreeViewNode treeNode = new TreeViewNode();
					treeNode.setKey(objBean.getObjectId());
					treeNode.setTitle(objBean.getObjectName());
					treeNode.setLazy(true);
					treeNode.setFolder(true);

					folderList.add(treeNode);
				}
			}
		}

		return folderList;
	}
	
}
