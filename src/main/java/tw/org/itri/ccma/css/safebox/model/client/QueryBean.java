package tw.org.itri.ccma.css.safebox.model.client;

/***
 * 前端 Client 送來的查詢檔案 Bean
 * 
 * @author A10138
 * 
 */
public class QueryBean {

	private String objectKey;
	private String etagId;

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getEtagId() {
		return etagId;
	}

	public void setEtagId(String etagId) {
		this.etagId = etagId;
	}

}
