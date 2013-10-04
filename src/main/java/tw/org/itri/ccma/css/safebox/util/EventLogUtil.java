package tw.org.itri.ccma.css.safebox.util;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import tw.org.itri.ccma.css.safebox.model.walrus.EBSClientEventLog;
import tw.org.itri.ccma.css.safebox.model.walrus.EBSWebClientEventLog;
import tw.org.itri.ccma.css.safebox.model.walrus.MergedEventsLog;

/***
 * 同步(合併) Event 用的相關 Util
 * 
 * @author Keanu
 * 
 */
public class EventLogUtil {

	public static MergedEventsLog genMergedEventsLog(int event_count, int merged_count, long sync_id) {
		MergedEventsLog mergedLog = new MergedEventsLog();
		mergedLog.setEventCount(event_count);
		mergedLog.setMergedCount(merged_count);
		mergedLog.setMergedDate(new Date());
		mergedLog.setSyncId(sync_id);

		return mergedLog;
	}

	public static EBSClientEventLog translateToEvent(EBSWebClientEventLog web_event, long sync_id) {
		if (null != web_event) {
			EBSClientEventLog eventLog = new EBSClientEventLog(sync_id);
			/**
			 * thanks for sonny, BeanUtils can copy properties from web_event to
			 * my new eventLog object
			 */
			BeanUtils.copyProperties(web_event, eventLog, new String[] { "syncId", "osName" });

			return eventLog;
		}

		return null;
	}
}
