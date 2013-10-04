package tw.org.itri.ccma.css.safebox.model;

/***
 * 包裝過後的檔案資訊，為的是要讓前端知道檔案的操作結果與訊息
 * 
 * @author A10138
 * 
 */
public class ObjectBeanMsgInfo extends ObjectBean {
	private boolean statusCode = false;
	private String msgInfo = "";

	public static ObjectBeanMsgInfo getInstance() {
		return new ObjectBeanMsgInfo();
	}

	public boolean isStatusCode() {
		return statusCode;
	}

	public void setStatusCode(boolean statusCode) {
		this.statusCode = statusCode;
		if (this.statusCode) {
			msgInfo = "";
		}
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
}
