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
 * 進行 Events 整併結果的紀錄資訊
 * 
 * @author A10138
 * 
 */
@Entity
@Table(name = "MergedEventsLog", schema = "public")
public class MergedEventsLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long logId;

	@Column(name = "mergedDate")
	@NotNull
	private Date mergedDate;

	@Column(name = "eventCount")
	@NotNull
	private long eventCount = 0;

	@Column(name = "mergedCount")
	@NotNull
	private long mergedCount = 0;

	@Column(name = "syncId")
	@NotNull
	private long syncId;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public Date getMergedDate() {
		return mergedDate;
	}

	public void setMergedDate(Date mergedDate) {
		this.mergedDate = mergedDate;
	}

	public long getEventCount() {
		return eventCount;
	}

	public void setEventCount(long eventCount) {
		this.eventCount = eventCount;
	}

	public long getMergedCount() {
		return mergedCount;
	}

	public void setMergedCount(long mergedCount) {
		this.mergedCount = mergedCount;
	}

	public long getSyncId() {
		return syncId;
	}

	public void setSyncId(long syncId) {
		this.syncId = syncId;
	}
}
