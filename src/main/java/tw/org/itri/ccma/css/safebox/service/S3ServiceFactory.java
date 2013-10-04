package tw.org.itri.ccma.css.safebox.service;

import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.util.ObjectMetadata;

/***
 * Walrus S3 �嚙賣謢塚蕭 Factory
 * 
 * @author A10138
 * 
 */
public class S3ServiceFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3ServiceFactory.class);

	private static Jets3tProperties jets3tProp;
	private static UUID uuid = UUID.randomUUID();
	
	static {
		jets3tProp = Jets3tProperties.getInstance("jets3t.properties");
	}

	/***
	 * ��嚙賡�蕭 jets3t �桀�嚙踝蕭謘潘蕭 S3Service 嚙賣�颲�
	 * 
	 * @param access_key
	 * @param secret_key
	 * @param client_type
	 * @return
	 */
	public static S3Service getS3Service(String access_key, String secret_key, final String client_type) {
		try {
			S3Service s3s = new RestS3Service(new AWSCredentials(access_key, secret_key), null, null, jets3tProp) {

				@Override
				protected HttpResponse performRequest(HttpUriRequest httpMethod, int[] expectedResponseCodes)
						throws ServiceException {
					/**
					 * ���嚙賡�憸梧蕭嚙踝蕭S3 嚙踝�嚙踝蕭鞈�蕭�蕭client 嚙踝���謢賃��嚙踝蕭蹎祗 HEADER �嚙賡嚙瘟BS Service�綜�隤蕭��
					 */
					String clientType;
					String instanceID = "web" + String.valueOf(uuid).replace("-", "");
					Member.getInstance().setInstanceKey(instanceID);
					
					if (Member.getInstance().getSyncID() == null) {
						clientType = "0";
					}
					
					else {
						clientType = Member.getInstance().getSyncID();
					}
					
					httpMethod.addHeader(ObjectMetadata.CLIENT_TYPE.toString(), clientType);
					httpMethod.addHeader(ObjectMetadata.INSTANCE_ID.toString(), instanceID);
					
					return super.performRequest(httpMethod, expectedResponseCodes);
				}

			};
			
			s3s.getHttpClient().getParams().setBooleanParameter("http.protocol.expect-continue", false);
			
			return s3s;
		} catch (S3ServiceException e) {
			LOGGER.error("=== get S3Service instance error: ", e);
			e.printStackTrace();
		}

		return null;
	}
	
}
