package tw.org.itri.ccma.css.safebox.model.client;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/***
 * �交 EBS Service ��unlock API ����
 * 
 * @author A10138
 * 
 */
public class UnlockResult {

	@JsonProperty("result")
	private String result;
	
	@JsonProperty("unlockresult")
	private String unlockResult;
	
	@JsonProperty("time")
	private String time;

	@JsonProperty("errors")
	private ArrayList<String> errors;
	
	/*
	@JsonIgnore
	public boolean isUnlock() {
		if ("true".equals(unlockResult)) {
			return true;
		}

		return false;
	}
	*/
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getUnlockResult() {
		return unlockResult;
	}

	public void setUnlockResult(String unlockResult) {
		this.unlockResult = unlockResult;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
}
