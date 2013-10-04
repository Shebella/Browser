package tw.org.itri.ccma.css.safebox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jets3t.service.ServiceException;
import org.jets3t.service.StorageObjectsChunk;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import tw.org.itri.ccma.css.safebox.model.Bucket;
import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBeanMsgInfo;
import tw.org.itri.ccma.css.safebox.model.client.RenameObjectResult;
import tw.org.itri.ccma.css.safebox.model.client.SyncRequestResult;
import tw.org.itri.ccma.css.safebox.model.client.UnlockResult;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService.UNLOCK_TYPE;
import tw.org.itri.ccma.css.safebox.util.ObjectBeanUtil;
import tw.org.itri.ccma.css.safebox.util.ObjectMetadata;

/***
 * �迤 Walrus S3 撖虫���
 * 
 * @author A10138
 * 
 */
@Service
@Qualifier("walrusDataService")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WalrusDataService extends AbsDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WalrusDataService.class);

	/**
	 * �芰��rename/move object ��雿��澆 EBS Service
	 */
	@Autowired
	IEventSyncService ebsService;

	@Override
	public Bucket createDefaultBucket(String bucket_name) {
		if (StringUtils.isNotEmpty(bucket_name)) {
			try {
				S3Bucket s3Bucket = getS3Service().createBucket(bucket_name);
				Bucket bucketObj = new Bucket();
				bucketObj.setBucketId(s3Bucket.getName());
				bucketObj.setBucketName(s3Bucket.getName());

				return bucketObj;
			} catch (Exception e) {
				LOGGER.error("=== create the default bucket error: ", e);
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public Bucket getBucket(String bucket_name) {
		try {
			S3Bucket s3bucket = getS3Service().getBucket(bucket_name);
			if (null != s3bucket) {
				Bucket bucketObj = new Bucket();
				bucketObj.setBucketId(s3bucket.getName());
				bucketObj.setBucketName(s3bucket.getName());
				return bucketObj;
			}
		} catch (Exception e) {

		} finally {
			shutdownService();
		}

		return null;
	}

	@Override
	public List<Bucket> getAllBuckets() {
		List<Bucket> bucketList = new ArrayList<Bucket>();

		try {
			S3Bucket[] bucketArr = getS3Service().listAllBuckets();
			for (int i = 0; i < bucketArr.length; i++) {
				Bucket bucketObj = new Bucket();
				bucketObj.setBucketId(bucketArr[i].getName());
				bucketObj.setBucketName(bucketArr[i].getName());

				S3Object[] objArr = getS3Service().listObjects(bucketArr[i].getName());
				bucketObj.setObjectCount(objArr.length);

				long bucketSize = 0;
				for (int j = 0; j < objArr.length; j++) {
					bucketSize += objArr[i].getContentLength();
				}
				bucketObj.setUsedSize(bucketSize);

				bucketList.add(bucketObj);
			}
		} catch (Exception e) {
			LOGGER.error("=== getAllBuckets() error: ", e);
			e.printStackTrace();
		} finally {
			shutdownService();
		}

		return bucketList;
	}

	/***
	 * �其遢 code �� Desktop client ��撘神瘜�listObject()
	 */
	@Override
	public List<ObjectBean> getObjectsByBucket(String bucket_name, String prefix, String delimiter,
			boolean fetch_children) {
		List<ObjectBean> objectList = new ArrayList<ObjectBean>();

		if (StringUtils.isNotEmpty(bucket_name)) {
			StorageObjectsChunk chunkArr[] = new StorageObjectsChunk[MAX_OBJECT_CHUNK];

			String lastKey = "";
			int chunkIdx = 0;
			prefix = ObjectBeanUtil.replacePrefixPath(prefix);
			do {
				try {
					chunkArr[chunkIdx] = getS3Service().listObjectsChunked(bucket_name, prefix, delimiter,
							MAX_LIST_NUM, lastKey, true);

					lastKey = chunkArr[chunkIdx].getPriorLastKey();
					chunkIdx++;
				} catch (Exception e) {
					lastKey = "";
				}
			} while (StringUtils.isNotEmpty(lastKey) && chunkIdx < MAX_OBJECT_CHUNK);

			try {
				for (int i = 0; i < chunkIdx; i++) {
					StorageObject[] objArr = chunkArr[i].getObjects();
					for (int j = 0; j < objArr.length; j++) {
						StorageObject s3Obj = getS3Service()
								.getObjectDetails(bucket_name, objArr[j].getKey());

						if (!s3Obj.getKey().endsWith(".eo") && !prefix.equals(s3Obj.getKey())) {

							ObjectBean objBean = ObjectBeanUtil.translateToObjectBean(s3Obj, SDF_MTIME);

							if (fetch_children) {
								objectList.addAll(getObjectsByBucket(bucket_name,
										ObjectBeanUtil.urlDecode(s3Obj.getKey()) + "/", "/", fetch_children));
							}
							objectList.add(objBean);
						}
					}

					String[] commonPrefixArr = chunkArr[i].getCommonPrefixes();
					for (int k = 0; k < commonPrefixArr.length; k++) {
						String objectKey = prefix + commonPrefixArr[k];

						if ("/".equals(objectKey)) {
							List<ObjectBean> subObjList = getObjectsByBucket(bucket_name, objectKey,
									delimiter, fetch_children);

							if (null == subObjList) {
								break;
							}

							objectList.addAll(subObjList);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("=== getObjectsByBucket() error: ", e);
				e.printStackTrace();
			} finally {
				shutdownService();
			}
		}

		return objectList;
	}

	@Override
	public S3Object getObject(String bucket_name, String object_key, boolean close_conn) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			try {
				return (S3Object) getS3Service().getObject(bucket_name, object_key);
			} catch (Exception e) {
				LOGGER.error("=== getObject() error: ", e);
				e.printStackTrace();
			} finally {
				if (close_conn) {
					shutdownService();
				}
			}
		}

		return null;
	}

	@Override
	public S3Object getObjectMeta(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			try {
				return (S3Object) getS3Service().getObjectDetails(bucket_name, object_key);
			} catch (ServiceException e) {
				LOGGER.error("=== get " + object_key + " metadata error: ", e);
			} finally {
				shutdownService();
			}
		}

		return null;
	}

	@Override
	public ObjectBeanMsgInfo getObjectBean(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			try {
				StorageObject objInfo = getS3Service().getObjectDetails(bucket_name, object_key);
				if (null != objInfo) {
					return ObjectBeanUtil.translateToObjectBean((S3Object) objInfo, SDF_MTIME);
				}
			} catch (Exception e) {
				LOGGER.error("=== get " + object_key + " metadata error: ", e);
			} finally {
				shutdownService();
			}
		}

		return new ObjectBeanMsgInfo();
	}

	@Override
	public ObjectBeanMsgInfo putDirObject(String bucket_name, String object_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			Boolean status = false;
			
			do {
				status = syncrequestV(bucket_name.substring(4));
			} while (!status);
			
			try {				
				S3Object dirObj = new S3Object("");

				dirObj.setKey(ObjectBeanUtil.urlEncode(object_key));
				dirObj.addMetadata("x-amz-meta-" + ObjectMetadata.MTIME.toString(), SDF_MTIME.format(new Date()));
				dirObj.setContentType(ObjectBean.CONTENT_TYPE_DIR);
				dirObj.setContentLength(0);
				dirObj = getS3Service().putObject(bucket_name, dirObj);

				return ObjectBeanUtil.translateToObjectBean(dirObj, SDF_MTIME);
			} catch (Exception e) {
				LOGGER.error("=== putDirObject() error: ", e);
				e.printStackTrace();
			} finally {
				unlockV(bucket_name.substring(4));
			}
		}

		return new ObjectBeanMsgInfo();
	}

	@Override
	public ObjectBeanMsgInfo putObject(String bucket_name, String object_key, byte[] byte_arr) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			Boolean status = false;
			
			do {
				status = syncrequestV(bucket_name.substring(4));
			} while (!status);
			
			try {
				S3Object fileObj = new S3Object(ObjectBeanUtil.urlEncode(object_key), byte_arr);
				fileObj.setBucketName(bucket_name);
				fileObj.setKey(ObjectBeanUtil.urlEncode(object_key));
				fileObj.addMetadata("x-amz-meta-" + ObjectMetadata.MTIME.toString(), SDF_MTIME.format(new Date()));
				fileObj = getS3Service().putObject(bucket_name, fileObj);

				return ObjectBeanUtil.translateToObjectBean(fileObj, SDF_MTIME);
			} catch (Exception e) {
				LOGGER.error("=== putObject() error: ", e);
				e.printStackTrace();
			} finally {
				unlockV(bucket_name.substring(4));
			}
		}

		return new ObjectBeanMsgInfo();
	}

	@Override
	public ObjectBeanMsgInfo moveObject(String account_member, String bucket_name, String src_obj_key,
			String new_obj_key) {
		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(src_obj_key)
				&& StringUtils.isNotEmpty(new_obj_key)) {
			Boolean status = false;
			
			do {
				status = syncrequestV(account_member);
			} while (!status);
			
			try {
				/**
				 * �� EBS Service �脰� rename/move ��雿�
				 */
				RenameObjectResult renameResult = ebsService.performRenameObject(account_member, bucket_name,
						ObjectBeanUtil.urlEncode(src_obj_key), ObjectBeanUtil.urlEncode(new_obj_key));
				if (null != renameResult) {
					return getObjectBean(bucket_name, new_obj_key);
				}
			} catch (Exception e) {
				LOGGER.error("=== moveObject() error: ", e);
				e.printStackTrace();
			} finally {
				unlockV(account_member);
			}
		}

		return new ObjectBeanMsgInfo();
	}

	@Override
	public ObjectBeanMsgInfo deleteObject(String bucket_name, String object_key) {
		ObjectBeanMsgInfo objBeanMsgInfo = new ObjectBeanMsgInfo();

		if (StringUtils.isNotEmpty(bucket_name) && StringUtils.isNotEmpty(object_key)) {
			Boolean status = false;
			
			do {
				status = syncrequestV(bucket_name.substring(4));
			} while (!status);
			
			try {
				List<ObjectBean> objList = getObjectsByBucket(bucket_name, object_key + "/", "/", true);
				if (0 < objList.size()) {
					for (ObjectBean objBean : objList) {
						getS3Service().deleteObject(bucket_name,
								ObjectBeanUtil.urlEncode(objBean.getObjectId()));
					}
				}

				getS3Service().deleteObject(bucket_name, ObjectBeanUtil.urlEncode(object_key));
				objBeanMsgInfo.setStatusCode(true);
			} catch (Exception e) {
				LOGGER.error("=== deleteObject() error: ", e);
				e.printStackTrace();
			} finally {
				unlockV(bucket_name.substring(4));
			}
		}

		return objBeanMsgInfo;
	}
	
	private boolean syncrequestV(String account) {
		Boolean result = false;
		String syncID = "-1";
		
		try {
			SyncRequestResult requestResult = 
				ebsService.performSyncRequest(account, Member.getInstance().getInstanceKey());
			
			result = Boolean.valueOf(requestResult.getResult());
			syncID = requestResult.getSyncId();
		} catch (Exception e) {
			result = false;
		} finally {
			if (result) {
				Member.getInstance().setSyncID(syncID);
			}
		}
		
		return result;
	}
	
	private boolean unlockV(String account) {
		Boolean result = false;
		UnlockResult unlockResult = 
			ebsService.performUnlockSync(account, Member.getInstance().getInstanceKey(), String.valueOf(UNLOCK_TYPE.SUCC));
		
		if (null != unlockResult) {
			result = Boolean.valueOf(unlockResult.getResult());
		}
		
		return result;
	}
}
