package tw.org.itri.ccma.css.safebox.model.walrus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/***
 * 儲存 Browser Events 的資訊
 * 
 * @author A10138
 * 
 */
@Entity
@Table(name = "web_opt_log", schema = "public")
public class EBSWebClientEventLog {
	public static enum CLIENT_TYPE {
		BROWSER_APP("webApp"), MOBILE_APP("mobileApp");
		private String text;

		CLIENT_TYPE(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventId;

	@Column(name = "rectime")
	@NotNull
	private Date serverReceivedTime;

	@Column(name = "time")
	private Date unknownTimeField;

	@Column(name = "cmpnm")
	private String computerName;

	@Column(name = "hwid")
	private String hardwareId;

	@Column(name = "os")
	private String osName;

	@Column(name = "osact")
	private String osAccount;

	@Column(name = "cssact")
	@NotNull
	private String userName;

	@Column(name = "clntipv4")
	private String clientIP;

	@Column(name = "srvipv4")
	private String serverIP;

	@Column(name = "sbxver")
	private String clientVersion;

	@Column(name = "op")
	@NotNull
	private String actionCode;

	@Column(name = "fpth")
	@NotNull
	private String objectKey;

	@Column(name = "fsz")
	@NotNull
	private long objectSize;

	@Column(name = "file_version")
	private String objectVersion;

	@Column(name = "isfolder")
	@NotNull
	private String folderFlag;

	@Column(name = "syncid")
	private Long syncId;

	@Column(name = "rslt")
	@NotNull
	private String actionResult;

	@Column(name = "inst_id")
	@NotNull
	private String instanceId;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public Date getServerReceivedTime() {
		return serverReceivedTime;
	}

	public void setServerReceivedTime(Date serverReceivedTime) {
		this.serverReceivedTime = serverReceivedTime;
	}

	public Date getUnknownTimeField() {
		return unknownTimeField;
	}

	public void setUnknownTimeField(Date unknownTimeField) {
		this.unknownTimeField = unknownTimeField;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsAccount() {
		return osAccount;
	}

	public void setOsAccount(String osAccount) {
		this.osAccount = osAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public long getObjectSize() {
		return objectSize;
	}

	public void setObjectSize(long objectSize) {
		this.objectSize = objectSize;
	}

	public String getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(String objectVersion) {
		this.objectVersion = objectVersion;
	}

	public String getFolderFlag() {
		return folderFlag;
	}

	public void setFolderFlag(String folderFlag) {
		this.folderFlag = folderFlag;
	}

	public Long getSyncId() {
		return syncId;
	}

	public void setSyncId(Long syncId) {
		this.syncId = syncId;
	}

	public String getActionResult() {
		return actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
