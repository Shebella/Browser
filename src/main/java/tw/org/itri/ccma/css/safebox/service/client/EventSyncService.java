package tw.org.itri.ccma.css.safebox.service.client;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.model.client.BucketInfoResult;
import tw.org.itri.ccma.css.safebox.model.client.RenameObjectResult;
import tw.org.itri.ccma.css.safebox.model.client.SyncRequestResult;
import tw.org.itri.ccma.css.safebox.model.client.UnlockResult;
import tw.org.itri.ccma.css.safebox.model.walrus.WalrusObject;
import tw.org.itri.ccma.css.safebox.repository.WalrusObjectRepository;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/***
 * �迤��Event �郊��撖虫�
 * 
 * @author Keanu
 * 
 */
@Service
public class EventSyncService extends AbsEventSyncService {
	//private static final Logger LOGGER = LoggerFactory.getLogger(EventSyncService.class);

	/**
	 * �望 Browser App 瘝� SyncId 璈嚗�甇支蝙�典��� SyncId 擗萇策 EBS Service嚗�閬� EBS
	 * Service 銝���霈�
	 */
	//private static final String FAKE_SYNC_ID = String.valueOf(Integer.MAX_VALUE);

	/**
	 * �湔�� Walrus Database ��Repository 隞
	 */
	@Autowired
	private WalrusObjectRepository walrusObjectRepository;

	@Override
	public List<WalrusObject> getWalrusObjectList(String bucket_name, List<String> object_key_list) {
		if (StringUtils.isNotEmpty(bucket_name) && null != object_key_list) {
			return walrusObjectRepository.findByBucketNameAndObjectKeyIn(bucket_name, object_key_list);
		}

		return null;
	}

	public String getUUIDByObject(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			WalrusObject walrusObj = walrusObjectRepository.findByBucketNameAndObjectKey(bucket_name,
					object_key);

			if (null != walrusObj) {
				return walrusObj.getObjectName();
			}
		}

		return "";
	}

	@Override
	public UnlockResult performUnlockSync(String member_account, String instance_key, String unlock_type) {
		if (StringUtils.isNotEmpty(member_account)) {
			try {
				/**
				 * 撠�甇亙���SyncId �澆 EBS Service 隞交�啁���銝虫��喳��clientType ��
				 * client 靘捱摰���BrowserApp/MobileApp
				 */
				MultivaluedMap<String, String> queryParamMap = new MultivaluedMapImpl();
				
				queryParamMap.add("account", member_account);
				queryParamMap.add("instanceid", instance_key);
				queryParamMap.add("status", unlock_type.toString());

				return (UnlockResult) getServiceResponse(getResource("/unlockV", queryParamMap).
						accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class), UnlockResult.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public SyncRequestResult performSyncRequest(String member_account, String instance_key) {
		if (StringUtils.isNotEmpty(member_account) && StringUtils.isNotEmpty(instance_key)) {
			try {
				/**
				 * �岫�澆 EBS Service �� SyncId嚗蒂銝�亦� clientType �� client 靘捱摰���
				 * BrowserApp/MobileApp
				 */
				MultivaluedMap<String, String> queryParamMap = new MultivaluedMapImpl();
				
				queryParamMap.add("account", member_account);
				queryParamMap.add("instanceid", instance_key);

				return (SyncRequestResult) getServiceResponse(getResource("/syncrequestV", queryParamMap).
						accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class), SyncRequestResult.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public RenameObjectResult performRenameObject(String member_account, String bucket_name, String src_name, String dest_name) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(src_name) && StringUtils.isNotEmpty(dest_name)) {
			try {
				/**
				 * �望 rename/move ��銝�閬� syncId嚗�隞亦�亙�亙���syncId 蝯�EBS Service
				 */
				MultivaluedMap<String, String> queryParamMap = new MultivaluedMapImpl();
				
				queryParamMap.add("account", member_account);
				queryParamMap.add("instanceid", Member.getInstance().getInstanceKey());
				queryParamMap.add("syncid", Member.getInstance().getSyncID());
				queryParamMap.add("src_object", src_name);
				queryParamMap.add("dest_object", dest_name);
				queryParamMap.add("bucket_name", bucket_name);
				/**
				 * ��POST �孵��澆
				 */
				return (RenameObjectResult) getServiceResponse(getResource("/renameobject", queryParamMap).
						accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class), RenameObjectResult.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public BucketInfoResult performBucketInfoQuery(String member_account, String bucket_name) {
		BucketInfoResult bucketResult = new BucketInfoResult();
		if (StringUtils.isNotEmpty(member_account) && StringUtils.isNotEmpty(bucket_name)) {
			try {
				MultivaluedMap<String, String> queryParamMap = new MultivaluedMapImpl();
				queryParamMap.add("account", member_account);
				queryParamMap.add("bucketname", bucket_name);

				/**
				 * ��GET �孵��澆
				 */
				bucketResult = (BucketInfoResult) getServiceResponse(
						getResource("/querybucketinfo", queryParamMap)
								.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class),
						BucketInfoResult.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bucketResult;
	}

}
