package tw.org.itri.ccma.css.safebox.util;

import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;

import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBeanMsgInfo;
import tw.org.itri.ccma.css.safebox.web.controller.AbsPagesController;

/***
 * 前端專用提供物件資訊的 Util
 * 
 * @author Keanu
 * 
 */
public class ObjectBeanUtil {

	public static ObjectBeanMsgInfo genObjectBean(String object_key, String etag_id) {
		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();
		objBean.setObjectId(object_key);
		objBean.setEtagId(etag_id);

		return objBean;
	}

	public static ObjectBeanMsgInfo translateToObjectBean(StorageObject obj, final DateFormat date_formatter) {
		ObjectBeanMsgInfo objBean = new ObjectBeanMsgInfo();

		if (null != obj) {
			objBean.setStatusCode(true);

			objBean.setObjectId(ObjectBeanUtil.urlDecode(obj.getKey()));
			objBean.setBucketName(obj.getBucketName());
			objBean.setObjectSize(obj.getContentLength());
			objBean.setEtagId(obj.getETag());

			String modifiedDate = (null != obj.getLastModifiedDate()) ? date_formatter.format(obj
					.getLastModifiedDate()) : date_formatter.format(new Date());
			objBean.setModifiedDate(modifiedDate);

			String tempName = obj.getName();
			if (ObjectBeanUtil.isDirObject((S3Object) obj)) {
				if (obj.getKey().endsWith("/")) {
					tempName = tempName.substring(0, tempName.length() - 1);
				}

				objBean.setType(ObjectBean.OBJECT_TYPE.FOLDER);
				objBean.setContentType(ObjectBean.CONTENT_TYPE_DIR);
				objBean.setObjectSize(-1);

				objBean.setClickAction(ObjectBeanUtil.getFolderLinkUrl(obj.getBucketName(),
						ObjectBeanUtil.urlDecode(obj.getKey())));

				int idxSlash = tempName.lastIndexOf("/");
				if (-1 < idxSlash) {
					tempName = tempName.substring(idxSlash + 1, tempName.length());
				}

				objBean.setObjectName(ObjectBeanUtil.urlDecode(tempName));
			} else {
				objBean.setType(ObjectBean.OBJECT_TYPE.FILE);
				objBean.setContentType(ObjectBean.CONTENT_TYPE_UNKNOW);

				objBean.setClickAction(ObjectBeanUtil.getObjectDownloadLinkUrl(objBean.getBucketName(),
						objBean.getObjectId()));

				int idxSlash = tempName.lastIndexOf("/");
				if (-1 < idxSlash) {
					tempName = tempName.substring(idxSlash + 1);
				}

				objBean.setObjectName(ObjectBeanUtil.urlDecode(tempName));
			}
		}

		return objBean;
	}

	public static boolean isDirObject(S3Object object) {
		return object.getKey().endsWith("/") || object.isDirectoryPlaceholder()
				|| "application/x-directory".equals(object.getContentType());
	}

	public static String getObjectDownloadLinkUrl(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			return "get/" + bucket_name + objectKeyEncode(object_key);
		}

		return "#";
	}

	public static String getFolderLinkUrl(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			return AbsPagesController.VIEW_PAGE_PREFIX + "files?bucket=" + bucket_name + "&folder="
					+ object_key;
		}

		return "#";
	}

	public static String replacePrefixPath(String prefix) {
		return prefix.replace("\\", "/").replace("+", "%2B").replace(" ", "+");
	}

	public static String objectKeyEncode(final String object_key) {
		String objectKey = object_key;
		objectKey = objectKey.replace("%", "%25").replace("#", "%23").replace(";", "%3b");

		return objectKey;
	}

	/***
	 * URL Encoding, code from emily's S3Assist in SafeBox desktop client
	 * 
	 * @param s
	 * @return
	 */
	public static String urlEncode(String s) {
		/*
		 * try { s= java.net.URLEncoder.encode(s,"utf-8"); } catch
		 * (UnsupportedEncodingException e) {} return s;
		 */
		String s1 = s;
		// s1= s1.replace("_", "%5F");
		s1 = s1.replace("+", "%2B");
		s1 = s1.replace(' ', '+');
		return s1;
	}

	public static String urlDecode(String s) {
		/*
		 * try { s= java.net.URLDecoder.decode(s,"utf-8"); } catch
		 * (UnsupportedEncodingException e) {} return s;
		 */
		String s1 = s;
		String s2, s3;
		int i, iFound, iFrom = 0;

		s1 = s.replace('+', ' ');

		// handle the %xx form and replace them for special characters (+~).
		while (iFrom < (s1.length() - 3)) {
			iFound = s1.indexOf('%', iFrom);
			if (iFound < 0 || (iFound + 3) > s1.length())
				break;
			s2 = s1.substring(iFound, iFound + 3);
			i = ConverNum(s2.charAt(1));
			i <<= 4;
			i |= ConverNum(s2.charAt(2));
			if (i >= 0x20 && i <= 0x7e) {
				s3 = "";
				s3 += (char) i;
				s1 = s1.replace(s2, s3);
				iFrom += 3;
			} else {
				iFrom += 1;
			}
		}

		return s1;
	}

	private static int ConverNum(char ch) {
		int n = 0;

		if (ch >= '0' && ch <= '9')
			n = ch - '0';
		else if (ch >= 'A' && ch <= 'F')
			n = 10 + ch - 'A';
		else if (ch >= 'a' && ch <= 'f')
			n = 10 + ch - 'a';
		return n;
	}

	public static Comparator<ObjectBean> getOrderByFolder() {
		Comparator<ObjectBean> byFolder = new Comparator<ObjectBean>() {
			public int compare(final ObjectBean obj1, final ObjectBean obj2) {
				if ("0".equals(obj1.getType())) {
					return -1;
				} else if ("1".equals(obj1.getType())) {
					return 1;
				}

				return 0;
			}
		};

		return byFolder;
	}

	public static Comparator<ObjectBean> getOrderByObjectKey() {
		Comparator<ObjectBean> byObjKey = new Comparator<ObjectBean>() {
			public int compare(final ObjectBean obj1, final ObjectBean obj2) {
				int cmpResult = obj1.getObjectId().compareTo(obj2.getObjectId());
				switch (cmpResult) {
				case 1:
					return -1;
				case -1:
					return 1;
				default:
					return 0;
				}
			}
		};

		return byObjKey;
	}
}
