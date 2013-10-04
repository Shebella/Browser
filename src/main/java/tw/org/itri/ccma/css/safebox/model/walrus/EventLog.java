package tw.org.itri.ccma.css.safebox.model.walrus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import tw.org.itri.ccma.css.safebox.model.JsonDateSerializer;

/***
 * 前端檔案操作的紀錄
 * 
 * @author A10138
 * 
 */
@Entity
@Table(name = "EventLog", schema = "public")
public class EventLog {
	public static enum ACTION_CODE {
		LOGIN("LOGIN"), UPLOAD("UPLOAD"), DELETE("DELETE"), RENAME("RENAME"), MOVE("MOVE");
		private String text;

		ACTION_CODE(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long logId;

	@NotNull
	@Column(name = "actionCode", nullable = false)
	private String actionCode;

	@NotNull
	@Column(name = "actionContent", nullable = false)
	private String actionContent;

	@NotNull
	@Column(name = "resultStatus", nullable = false)
	private String resultStatus;

	@NotNull
	@Column(name = "actionDate", nullable = false)
	private Date actionDate;

	@NotNull
	@Column(name = "actionUser", nullable = false)
	private String actionUser;

	@PrePersist
	public void prePersist() {
		actionDate = new Date();
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionContent() {
		return actionContent;
	}

	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getActionUser() {
		return actionUser;
	}

	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}
}
