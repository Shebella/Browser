package tw.org.itri.ccma.css.safebox.model.client;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/***
 * �交 EBS Service ��renameObject API ����
 * 
 * @author A10138
 * 
 */
public class RenameObjectResult {

	@JsonProperty("result")
	private String result;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("success")
	private String success;

	@JsonProperty("errors")
	private ArrayList<String> errors;
	
	public String getResult() {
		return result;
	}

	public void setResults(String result) {
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	
}
