package tw.org.itri.ccma.css.safebox.model;

/***
 * 前端顯示用的 Bucket 使用量資訊
 * 
 * @author A10138
 * 
 */
public class Bucket {

	private String bucketId;
	private String bucketName;
	private int objectCount;
	private long usedSize;

	public String getBucketId() {
		return bucketId;
	}

	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public int getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}

	public long getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(long usedSize) {
		this.usedSize = usedSize;
	}
}
