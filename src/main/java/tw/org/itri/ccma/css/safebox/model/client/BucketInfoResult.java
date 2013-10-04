package tw.org.itri.ccma.css.safebox.model.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/***
 * 接收 EBS Service 的 queryBucket API 的結果，並且忽略部份欄位資訊
 * 
 * @author A10138
 * 
 */
@JsonIgnoreProperties({ "owner_id", "totalUsed", "maxSize", "isVersioning" })
public class BucketInfoResult {

	private long objectCount = 0;

	public long getObjectCount() {
		return objectCount;
	}

	public void setObjectCount(long objectCount) {
		this.objectCount = objectCount;
	}

}
