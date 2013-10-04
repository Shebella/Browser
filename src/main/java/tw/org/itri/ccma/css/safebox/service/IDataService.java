package tw.org.itri.ccma.css.safebox.service;

import java.util.List;

import org.jets3t.service.model.S3Object;

import tw.org.itri.ccma.css.safebox.model.Bucket;
import tw.org.itri.ccma.css.safebox.model.ObjectBean;
import tw.org.itri.ccma.css.safebox.model.ObjectBeanMsgInfo;

public interface IDataService {

	public abstract Bucket createDefaultBucket(String bucket_name);

	public abstract Bucket getBucket(String bucket_name);

	public abstract List<Bucket> getAllBuckets();

	public abstract List<ObjectBean> getObjectsByBucket(String bucket_name, String prefix, String delimiter,
			boolean fetch_children);

	public abstract S3Object getObject(String bucket_name, String object_key, boolean close_conn);

	public abstract S3Object getObjectMeta(String bucket_name, String object_key);

	public abstract ObjectBeanMsgInfo getObjectBean(String bucket_name, String object_key);

	public abstract ObjectBeanMsgInfo putDirObject(String bucket_name, String object_key);

	public abstract ObjectBeanMsgInfo putObject(String bucket_name, String object_key, byte[] byte_arr);

	public abstract ObjectBeanMsgInfo moveObject(String member_account, String bucket_name,
			String src_obj_key, String new_obj_key);

	public abstract ObjectBeanMsgInfo deleteObject(String bucket_name, String object_key);

}