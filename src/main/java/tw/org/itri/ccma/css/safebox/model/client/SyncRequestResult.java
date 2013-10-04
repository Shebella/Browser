package tw.org.itri.ccma.css.safebox.model.client;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/***
 * �交 EBS Service ��syncRequest API 蝯�
 * 
 * @author A10138
 * 
 */
public class SyncRequestResult {

	@JsonProperty("result")
	private String result;
	
	@JsonProperty("syncRequestResult")
	private String syncRequestResult;
	
	@JsonProperty("syncId")
	private String syncId;
	
	@JsonProperty("systeminfo")
	@JsonDeserialize(as = SystemInfo.class)
	private SystemInfo systemInfo;
	
	@JsonProperty("errors")
	private ArrayList<String> errors;
	
	/*
	@JsonProperty("syncData")
	@JsonDeserialize(as = SyncData.class)
	private SyncData syncData;
	*/
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getSyncRequestResult() {
		return syncRequestResult;
	}

	public void setSyncRequestResult(String syncRequestResult) {
		this.syncRequestResult = syncRequestResult;
	}
	
	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	
	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	/*
	@JsonIgnore
	public boolean isGotLock() {
		if ("true".equals(gotLock)) {
			return true;
		}

		return false;
	}

	public String getGotLock() {
		return gotLock;
	}

	public void setGotLock(String gotLock) {
		this.gotLock = gotLock;
	}
	
	public SyncData getSyncData() {
		return syncData;
	}

	public void setSyncData(SyncData syncData) {
		this.syncData = syncData;
	}
	
	public class SyncData {
		public SyncData() {

		}

		@JsonProperty("syncid")
		private String syncId;

		public String getSyncId() {
			return syncId;
		}

		public void setSyncId(String syncId) {
			this.syncId = syncId;
		}
	}
	*/
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class SystemInfo {
		
		private String result;
		private String serverTime;
		
		public SystemInfo() {

		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
		
		public String getServerTime() {
			return serverTime;
		}

		public void setServerTime(String serverTime) {
			this.serverTime = serverTime;
		}
		
	}
	
}
