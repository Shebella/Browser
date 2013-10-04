package tw.org.itri.ccma.css.safebox.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import tw.org.itri.ccma.css.safebox.config.AccountConfig;
import tw.org.itri.ccma.css.safebox.config.AppConfig;
import tw.org.itri.ccma.css.safebox.config.ClientConfig;
import tw.org.itri.ccma.css.safebox.model.Member;

@Component
@Qualifier("itriLoginAdapter")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ItriLoginAdapter extends AbsLoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItriLoginAdapter.class);

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ClientConfig clientConfig;

	@Autowired
	private AccountConfig accountConfig;

	@Autowired
	private WalrusDataService dataService;

	@Override
	public Member login(String user_id, String password) {
		if (null != accountConfig) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(clientConfig.getServiceURL() + accountConfig.getServicePath());

			httpGet.setHeader("user", user_id);
			httpGet.setHeader("password", password);

			InputStream iStream = null;
			StringBuffer stb = new StringBuffer();
			try {
				HttpResponse httpResp = httpClient.execute(httpGet);
				int statusCode = httpResp.getStatusLine().getStatusCode();

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("=== login status: " + statusCode);
				}

				if (HttpStatus.SC_OK == statusCode) {
					HttpEntity httpEntity = httpResp.getEntity();

					iStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
					String bufferContent = "";
					while (StringUtils.isNotEmpty(bufferContent = reader.readLine())) {
						stb.append(bufferContent);

						bufferContent = reader.readLine();
					}

				}

			} catch (Exception e) {
				LOGGER.error("=== login error: ", e);
				e.printStackTrace();
			} finally {
				try {
					if (null != iStream) {
						iStream.close();
					}
				} catch (IOException e) {
				}

				httpClient.getConnectionManager().shutdown();
			}

			String respContent = stb.toString();
			if (StringUtils.isNotEmpty(respContent)) {
				Member member = new Member();

				String[] respArr = respContent.split("\t");
				for (int i = 0; i < respArr.length; i++) {
					if (user_id.equals(respArr[i])) {
						String accessKey = respArr[i + 1];
						String secretKey = respArr[i + 2];

						int skIdx = secretKey.indexOf('\n');
						if (0 > skIdx)
							skIdx = secretKey.indexOf('\r');
						if (1 < skIdx) {
							secretKey = secretKey.substring(0, skIdx);
							secretKey = secretKey.replace('\r', '\0');
						}

						member.setUserId(respArr[i].trim());
						member.setUserName(member.getUserId());
						member.setAccessKey(accessKey);
						member.setSecretKey(secretKey);

						break;
					}
				}

				if (StringUtils.isNotEmpty(member.getAccessKey())
						&& StringUtils.isNotEmpty(member.getSecretKey())) {
					return member;
				}
			}
		}

		return null;
	}

	@Override
	public void afterLogin(Member member) {
		if (null != member) {
			/**
			 * 完成登入後把 Member 的 AccessKey/SecretKey/ClientType 三個資訊設到 dataService
			 * 中，為的是要讓預設的 Bucket 可以先被建立出來
			 */
			dataService.setUserAccessKey(member.getAccessKey());
			dataService.setUserSecretKey(member.getSecretKey());

			if (0 == dataService.getAllBuckets().size()) {
				dataService.createDefaultBucket(appConfig.getDefaultBucketName(member.getUserId()));
			}

			dataService.shutdownService();
		}
	}
}
