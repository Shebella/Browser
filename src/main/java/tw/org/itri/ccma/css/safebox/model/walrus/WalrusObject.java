package tw.org.itri.ccma.css.safebox.model.walrus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * Walrus 的物件
 * 
 * @author A10138
 * 
 */
@Entity
@Table(name = "objects", schema = "public")
public class WalrusObject {

	@Id
	@Column(name = "object_id")
	private long objectId;

	@Column(name = "bucket_name")
	private String bucketName;

	@Column(name = "content_disposition")
	private String contentDisposition;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name = "etag")
	private String etag;

	@Column(name = "global_read")
	private boolean globalRead;

	@Column(name = "global_read_acp")
	private boolean globalReadAcp;

	@Column(name = "global_write")
	private boolean globalWrite;

	@Column(name = "global_write_acp")
	private boolean globalWriteAcp;

	@Column(name = "is_last")
	private boolean isLast;

	@Column(name = "last_modified")
	private Date lastModifiedDate;

	@Column(name = "obj_seq")
	private int objSeq;

	@Column(name = "object_key")
	private String objectKey;

	@Column(name = "object_name")
	private String objectName;

	@Column(name = "owner_id")
	private String ownerId;

	@Column(name = "size")
	private long objSize;

	@Column(name = "storage_class")
	private String storageClass;

	@Column(name = "version_id")
	private String versionId;

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public boolean isGlobalRead() {
		return globalRead;
	}

	public void setGlobalRead(boolean globalRead) {
		this.globalRead = globalRead;
	}

	public boolean isGlobalReadAcp() {
		return globalReadAcp;
	}

	public void setGlobalReadAcp(boolean globalReadAcp) {
		this.globalReadAcp = globalReadAcp;
	}

	public boolean isGlobalWrite() {
		return globalWrite;
	}

	public void setGlobalWrite(boolean globalWrite) {
		this.globalWrite = globalWrite;
	}

	public boolean isGlobalWriteAcp() {
		return globalWriteAcp;
	}

	public void setGlobalWriteAcp(boolean globalWriteAcp) {
		this.globalWriteAcp = globalWriteAcp;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getObjSeq() {
		return objSeq;
	}

	public void setObjSeq(int objSeq) {
		this.objSeq = objSeq;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public long getObjSize() {
		return objSize;
	}

	public void setObjSize(long objSize) {
		this.objSize = objSize;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
}
