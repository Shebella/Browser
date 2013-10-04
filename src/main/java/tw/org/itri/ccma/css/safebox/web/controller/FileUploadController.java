package tw.org.itri.ccma.css.safebox.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.walrus.EventLog.ACTION_CODE;

/***
 * 檔案上傳 Controller
 * 
 * @author Keanu
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController extends AbsFrontendController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	private String folderStart = "";

	@RequestMapping(value = "/form/{bucketName}/**", method = RequestMethod.GET)
	public String showForm(@PathVariable("bucketName") String bucket_name, HttpServletRequest request,
			Model model) {
		model.addAttribute("bucketName", bucket_name);

		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			folderStart = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}
		model.addAttribute("folderStart", folderStart);

		return AbsPagesController.VIEW_PAGE_PREFIX + "uploadForm";
	}

	/***
	 * 處理檔案上傳
	 */
	@RequestMapping(value = "/{bucketName}/**", method = { RequestMethod.POST, RequestMethod.PUT })
	@Transactional
	public String handleFormUpload(@PathVariable("bucketName") String bucket_name,
			@RequestParam("file") MultipartFile file, HttpServletRequest request,
			RedirectAttributes redirect_attr, Model model) {
		boolean statusCode = false;
		String msgInfo = "file is empty";

		String restOfTheUrl = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			folderStart = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		}

		String objKey = file.getOriginalFilename();

		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(objKey) && !objKey.endsWith("/")) {
			if (StringUtils.isNotEmpty(folderStart) && !folderStart.endsWith("/")) {
				objKey = folderStart + "/" + objKey;
			} else {
				objKey = folderStart + objKey;
			}

			try {
				if (!getDataService().isDirObjectExisted(bucket_name, objKey)) {
					/**
					 * 將上傳成功的檔案直接送入 Walrus 中
					 */
					ObjectBean uploadObj = getDataService().putObject(bucket_name, objKey, file.getBytes());
					if (null != uploadObj) {
						msgInfo = "Your file " + file.getOriginalFilename() + " was uploaded successfully";
						statusCode = true;

						getLogService().logEvent(ACTION_CODE.UPLOAD,
								"You added the file " + file.getOriginalFilename() + ".", true);
					} else {
						LOGGER.warn("=== putobject() is null, bucketName: " + bucket_name + ", fileName: "
								+ objKey);
					}
				} else {
					msgInfo = "The upload was canceled, " + file.getOriginalFilename()
							+ " is already existed";
				}

			} catch (IOException e) {
				LOGGER.error("=== upload file error: ", e);
				e.printStackTrace();
			}
		}

		/**
		 * 判斷前端頁面是否為多檔上傳或一般傳統檔案上傳，若為一般傳統檔案上傳則會由 need302 資訊則幫忙轉址至 Files 頁面
		 * Controller
		 */
		String need302 = request.getParameter("need302");
		if (StringUtils.isNotEmpty(need302)) {
			redirect_attr.addFlashAttribute("msgInfo", msgInfo);

			try {
				folderStart = "/" + URLEncoder.encode(folderStart.substring(1), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if ("redirect".equals(need302)) {
				return "redirect:/" + AbsPagesController.VIEW_PAGE_PREFIX + "files?bucket=" + bucket_name
						+ "&folder=" + folderStart;
			} else {
				// request.getSession().setAttribute("msgInfo", msgInfo);
			}
		} else {
			model.addAttribute("msgInfo", msgInfo);
			model.addAttribute("statusCode", statusCode);
		}

		return "msgInfo";
	}
}
