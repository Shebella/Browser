package tw.org.itri.ccma.css.safebox.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;

/***
 * 系統共用設定
 * 
 * @author A10138
 * 
 */
@Configuration
public class AppConfig {
	// 預設 SESSION 變數名稱
	private static final String MEMBER_SESSION_NAME = "member";
	// 預設 Bucket 名稱的 Prefix 字串
	private static final String BUCKET_PATTERN_PREFIX = "bkt-";

	public String getMemberSessionName() {
		return MEMBER_SESSION_NAME;
	}

	public String getDefaultBucketName(String member_name) {
		return BUCKET_PATTERN_PREFIX + StringUtils.lowerCase(member_name);
	}

}
