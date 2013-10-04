package tw.org.itri.ccma.css.safebox.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import tw.org.itri.ccma.css.safebox.service.AbsDataService;
import tw.org.itri.ccma.css.safebox.util.ObjectBeanUtil;

/***
 * 前端下載檔案用 Controller
 * 
 * @author Keanu
 * 
 */
@Controller
@RequestMapping(value = "/get")
@Transactional(readOnly = true)
public class DownloadObjectController extends AbsFrontendController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadObjectController.class);

	@RequestMapping(value = "/{bucketName}/**", method = RequestMethod.GET, produces = "application/octet-stream")
	@ResponseBody
	public byte[] getObject(@PathVariable("bucketName") String bucket_name, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String objKey = "";
		String restOfTheUrl = request.getPathInfo();
		String parentFolder = "";
		if (StringUtils.isNotEmpty(restOfTheUrl)) {
			parentFolder = restOfTheUrl.substring(restOfTheUrl.indexOf(bucket_name) + bucket_name.length());
		} else {
			restOfTheUrl = (String) request
					.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			parentFolder = restOfTheUrl.replace("/" + bucket_name, "").substring(1);
		}

		objKey = parentFolder.substring(parentFolder.indexOf("/"), parentFolder.length());

		AbsDataService dataService = getDataService();
		S3Object objDownload = null;
		try {

			/**
			 * 下載檔案會有兩道手序，第一動為至 Walrus 取回該 Storage 物件
			 */
			if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(objKey)) {
				objDownload = dataService.getObject(bucket_name, ObjectBeanUtil.urlEncode(objKey), false);
			}

			if (null != objDownload) {
				String fileName = objKey.substring(objKey.lastIndexOf("/") + 1, objKey.length());

				/**
				 * IE sucks, 要幫忙設 User-Agent HEADER 資訊 IE 才可以正確處理下載的檔案
				 */
				if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				response.setContentLength((int) objDownload.getContentLength());

				/**
				 * 第二動為將該 Storage 物件以 input stream 方式回傳給 Browser
				 */
				return ServiceUtils.readInputStreamToBytes(objDownload.getDataInputStream());
			}
		} catch (Exception e) {
			LOGGER.error("=== download error: ", e);
			e.printStackTrace();
		} finally {
			response.flushBuffer();

			if (null != objDownload) {
				objDownload.closeDataInputStream();
			}

			dataService.shutdownService();
		}

		return null;
	}
}
