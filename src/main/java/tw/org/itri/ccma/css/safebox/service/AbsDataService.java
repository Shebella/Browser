package tw.org.itri.ccma.css.safebox.service;

import java.text.SimpleDateFormat;

import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tw.org.itri.ccma.css.safebox.config.ClientConfig;
import tw.org.itri.ccma.css.safebox.service.schedule.IWorker;
import tw.org.itri.ccma.css.safebox.util.ObjectBeanUtil;

/***
 * �綽��S3 �嚙賣謢塚蕭嚙踝�嚙�
 * 
 * @author A10138
 * 
 */
public abstract class AbsDataService implements IDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbsDataService.class);

	// 嚙踝��Ｘ�嚙賢赯菜�嚙�	public static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy HH:mm");
	// Walrus 嚙質嚙踝蕭�踐僱�蕭
	public static final SimpleDateFormat SDF_MTIME = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
	// 嚙踐�頨�listObject 嚙質�嚙賡��蕭
	public static final int MAX_LIST_NUM = 2000;
	// 嚙踐�頨�Walrus Object Chunk 嚙質�嚙賡��蕭
	public static final int MAX_OBJECT_CHUNK = 16;

	private String userAccessKey;
	private String userSecretKey;
	private String clientTypeForEBS;

	private S3Service s3Service;

	@Autowired
	private ClientConfig clientConfig;

	@Autowired
	@Qualifier("mergeEventWorker")
	IWorker eventWorker;

	public void setUserAccessKey(String userAccessKey) {
		this.userAccessKey = userAccessKey;
	}

	public void setUserSecretKey(String userSecretKey) {
		this.userSecretKey = userSecretKey;
	}

	public void setClientTypeForEBS(String clientTypeForEBS) {
		this.clientTypeForEBS = clientTypeForEBS;
	}

	protected S3Service getS3Service(String access_key, String secret_key) {
		s3Service = S3ServiceFactory.getS3Service(access_key, secret_key, clientConfig.getClientTypeForEBS());
		return s3Service;
	}

	protected S3Service getS3Service() {
		s3Service = S3ServiceFactory.getS3Service(userAccessKey, userSecretKey,
				clientConfig.getClientTypeForEBS());
		return s3Service;
	}

	public void shutdownService() {
		shutdownService(s3Service);
	}

	public void shutdownService(S3Service s3_service) {

		try {
			if (null != s3_service) {
				s3_service.shutdown();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} finally {
			eventWorker.doWork(clientTypeForEBS);
		}
	}
	
	private StorageObject getObjectDetail(String bucket_name, String object_key) {
		try {
			return getS3Service().getObjectDetails(bucket_name, object_key);
		} catch (ServiceException se) {
			if (se.getResponseCode() != 404) {
				LOGGER.warn("=== check object " + object_key + " by bueckt " + bucket_name
						+ " existed error: ", se);
			}
		} catch (Exception e) {
			LOGGER.warn("=== check object " + object_key + " by bueckt " + bucket_name + " existed error: "
					+ e.getMessage());
		} finally {
			shutdownService();
		}

		return null;
	}

	/***
	 * 嚙賢嚙踝蕭��嚙踝嚙踝蕭��嚙踝蕭object_key 嚙質�謢塗祗
	 * 
	 * @param bucket_name
	 * @param object_key
	 * @return
	 */
	public boolean isDirObjectExisted(String bucket_name, String object_key) {
		StorageObject checkObj = getObjectDetail(bucket_name, object_key);
		if (null != checkObj && ObjectBeanUtil.isDirObject((S3Object) checkObj)) {
			return true;
		}

		return false;
	}

	/***
	 * 嚙賢嚙踝蕭��嚙踝嚙踝蕭嚙緻bject_key 嚙質�謢塗祗
	 * 
	 * @param bucket_name
	 * @param object_key
	 * @return
	 */
	public boolean isObjectExisted(String bucket_name, String object_key) {
		if (null != getObjectDetail(bucket_name, object_key)) {
			return true;
		}

		return false;
	}
}
