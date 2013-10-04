package tw.org.itri.ccma.css.safebox.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/***
 * 前端使用的檔案資訊
 * 
 * @author A10138
 * 
 */
public class ObjectBean implements Comparable<ObjectBean> {
	public static enum OBJECT_TYPE {
		FOLDER("0"), FILE("1");

		private String text;

		OBJECT_TYPE(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	public final static String CONTENT_TYPE_DIR = "application/x-directory";
	public final static String CONTENT_TYPE_UNKNOW = "unknow";

	private String objectId;
	private String bucketName;
	private String objectUUID = "";

	private String etagId;
	private String objectName;
	private long objectSize;
	private OBJECT_TYPE type = OBJECT_TYPE.FILE;
	private String contentType;
	private String modifiedDate;

	private String clickAction;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getObjectUUID() {
		return objectUUID;
	}

	public void setObjectUUID(String objectUUID) {
		this.objectUUID = objectUUID;
	}

	public String getEtagId() {
		return etagId;
	}

	public void setEtagId(String etagId) {
		this.etagId = etagId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public long getObjectSize() {
		return objectSize;
	}

	public void setObjectSize(long objectSize) {
		this.objectSize = objectSize;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	public String getType() {
		return type.toString();
	}

	public void setType(OBJECT_TYPE type) {
		this.type = type;
	}

	@Override
	public int compareTo(ObjectBean that) {
		/**
		 * 排序方式為目錄在前檔案在後，並且依照檔案名稱排序
		 */
		return ComparisonChain.start()
				.compare(this.getType(), that.getType(), Ordering.natural().nullsLast())
				.compare(this.getObjectId(), that.getObjectId(), Ordering.natural().nullsLast()).result();
	}
}
