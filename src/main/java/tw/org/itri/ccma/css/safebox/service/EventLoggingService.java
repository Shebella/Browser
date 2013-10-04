package tw.org.itri.ccma.css.safebox.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.itri.ccma.css.safebox.model.Member;
import tw.org.itri.ccma.css.safebox.model.walrus.EventLog;
import tw.org.itri.ccma.css.safebox.model.walrus.EventLog.ACTION_CODE;
import tw.org.itri.ccma.css.safebox.repository.EventLogRepository;

/***
 * 前端操作行為記錄服務
 * 
 * @author A10138
 * 
 */
@Service
public class EventLoggingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventLoggingService.class);

	// 前端條列筆數上限
	private static final int LIST_MAX_SIZE = 200;
	// 前端顯示日期格式
	private static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy");

	@Autowired
	private EventLogRepository eventLogRepository;

	private Member memberInfo;

	/***
	 * 記錄行為
	 * 
	 * @param action_code
	 * @param action_content
	 * @param status
	 */
	@Transactional
	public void logEvent(ACTION_CODE action_code, String action_content, boolean status) {
		EventLog eventLog = new EventLog();
		eventLog.setActionCode(action_code.toString());
		eventLog.setActionContent(action_content);
		eventLog.setActionUser(memberInfo.getUserId());
		eventLog.setResultStatus(Boolean.toString(status));

		eventLogRepository.save(eventLog);
	}

	/***
	 * 依登入的會員取得指定日期的歷史紀錄
	 * 
	 * @param log_date
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<EventLog> getLogsByMember(String log_date) {
		List<EventLog> logList = new ArrayList<EventLog>();

		Date logDate = null;
		if (StringUtils.isNotEmpty(log_date)) {
			try {
				logDate = SDF.parse(log_date.trim());
			} catch (Exception e) {
				LOGGER.warn("=== parsing date format error: ", e);
				e.printStackTrace();
			}
		}

		if (null != logDate) {
			/**
			 * 如果有指定日期則只會列出 LIST_MAX_SIZE 筆
			 */
			logList = eventLogRepository.findByDate(logDate, (new DateTime(logDate)).plusDays(1).toDate(),
					new PageRequest(0, LIST_MAX_SIZE, Direction.DESC, "actionDate"));
		} else {
			/**
			 * 沒有指定日期列出分頁後的 LIST_MAX_SIZE 筆
			 */
			Page<EventLog> pagedList = eventLogRepository.findAllByMember(getMemberInfo().getUserId(),
					new PageRequest(0, LIST_MAX_SIZE, Direction.DESC, "actionDate"));
			return pagedList.getContent();
		}

		return logList;
	}

	public Member getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(Member memberInfo) {
		this.memberInfo = memberInfo;
	}

}
