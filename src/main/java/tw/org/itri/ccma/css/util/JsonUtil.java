package tw.org.itri.ccma.css.util;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 前端專用物件互轉 JSON 的 Util
 * 
 * @author Keanu
 * 
 */
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Object convertToObject(String json_str, Class<?> clazz) {
		if (StringUtils.isNotEmpty(json_str)) {
			try {
				return mapper.readValue(mapper.getJsonFactory().createJsonParser(json_str), clazz);
			} catch (Exception e) {
				LOGGER.error("=== convert to object error: ", e);
				e.printStackTrace();
			}
		}

		return null;
	}

	public static String convertToJSON(Object obj) {
		String jsonStr = "";

		if (null != obj) {
			try {
				jsonStr = mapper.writeValueAsString(obj);
			} catch (Exception e) {
				LOGGER.error("=== convert to json string error: ", e);
				e.printStackTrace();
			}
		}

		return jsonStr;
	}
}
