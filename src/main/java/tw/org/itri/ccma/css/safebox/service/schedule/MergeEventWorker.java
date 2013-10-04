package tw.org.itri.ccma.css.safebox.service.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import tw.org.itri.ccma.css.safebox.model.client.SyncRequestResult;
import tw.org.itri.ccma.css.safebox.model.client.UnlockResult;
import tw.org.itri.ccma.css.safebox.model.walrus.EBSClientEventLog;
import tw.org.itri.ccma.css.safebox.model.walrus.EBSWebClientEventLog;
import tw.org.itri.ccma.css.safebox.repository.EBSClientEventLogRepository;
import tw.org.itri.ccma.css.safebox.repository.EBSWebClientEventLogRepository;
import tw.org.itri.ccma.css.safebox.repository.MergedEventsLogRepository;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService;
import tw.org.itri.ccma.css.safebox.service.client.IEventSyncService.UNLOCK_TYPE;
import tw.org.itri.ccma.css.safebox.util.EventLogUtil;

import com.google.common.collect.ImmutableList;

/***
 * �迤��Event �郊(�蔥)��撖虫�
 * 
 * @author Keanu
 * 
 */
@Component()
@Qualifier("mergeEventWorker")
public class MergeEventWorker implements IWorker {
	private static final Logger LOGGER = LoggerFactory.getLogger(MergeEventWorker.class);

	/**
	 * ��啣���EBS Service �砌�撠勗�函� opt_log table ��Repository 隞
	 */
	@Autowired
	private EBSClientEventLogRepository eventLogRepository;

	/**
	 * 閬�銵�甇��蔥)��web_opt_log table ��Repository 隞
	 */
	@Autowired
	private EBSWebClientEventLogRepository webEventLogRepository;

	/**
	 * 撠�甇��蔥)蝯�閮��唳迨 merged_events table ��Repository 隞
	 */
	@Autowired
	private MergedEventsLogRepository mergedEventLogRepository;

	@Autowired
	IEventSyncService ebsService;

	@Override
	// @Scheduled(cron = "${cron.merge.event.task}")
	// @Async
	public void doWork(String client_type) {
		long syncId = -1;

		/**
		 * ��敺���啁���� web events
		 */
		List<EBSWebClientEventLog> webEventList = webEventLogRepository.findAll();

		if (0 < webEventList.size()) {
			LOGGER.info("=== total web event need to merge: " + webEventList.size());

			UNLOCK_TYPE mergedFlag = UNLOCK_TYPE.FAIL;

			try {
				/**
				 * �澆 EBS Service 隞亙�敺�syncId
				 */
				SyncRequestResult requestResult = ebsService.performSyncRequest(client_type, client_type);
				if (null != requestResult) {
					syncId = Long.valueOf(requestResult.getSyncId());
				}

				/**
				 * �脰��郊(�蔥)��
				 */
				if (-1 < syncId && performMergeEvent(ImmutableList.copyOf(webEventList), syncId)) {
					mergedFlag = UNLOCK_TYPE.SUCC;
				}

			} catch (Exception e) {
				LOGGER.error("=== MergeEventWorker error: ", e);
			} finally {
				if (-1 < syncId) {
					/**
					 * �∟��郊(�蔥)���臬���賢��EBS Service �湔甇斗活 syncId ���
					 */
					LOGGER.info("=== trying to unlock... " + syncId);
					UnlockResult unlockResult = ebsService.performUnlockSync(client_type, client_type, String.valueOf(mergedFlag));
					if (null != unlockResult) {
						LOGGER.info("=== unlock result(" + syncId + "): " + unlockResult.getUnlockResult());
					}
				}
			}
		}
	}

	private boolean performMergeEvent(List<EBSWebClientEventLog> webEventList, long sync_id) {
		LOGGER.info("=== got sync id from EBS Service: " + sync_id + ", events count: " + webEventList.size());

		boolean mergedFlag = false;
		int mergedSuccessCount = 0;
		try {
			/**
			 * 撠�蝑�web_opt_log table ��event 撖怠 opt_log table 銝�
			 */
			for (EBSWebClientEventLog webEvent : webEventList) {
				EBSClientEventLog clientEvent = EventLogUtil.translateToEvent(webEvent, sync_id);
				if (null != clientEvent && null != eventLogRepository.save(clientEvent)) {
					mergedSuccessCount++;
				}
			}

			/**
			 * �湔�芷 web_opt_log table ��events
			 */
			webEventLogRepository.deleteInBatch(webEventList);

			mergedFlag = true;
		} catch (Exception e) {
			LOGGER.error("=== merged web event(" + sync_id + ") error: ", e);
		} finally {
			/**
			 * 撠�甇��蔥)蝯�撖怠 merged_events_log table 銝剖��箇���
			 */
			try {
				LOGGER.info("=== updated " + mergedSuccessCount + " events for syncId: " + sync_id);
				mergedEventLogRepository.save(EventLogUtil.genMergedEventsLog(webEventList.size(),
						mergedSuccessCount, sync_id));
			} catch (Exception e) {
				LOGGER.error("=== update merged web event(" + sync_id + "), eventSize=" + webEventList.size()
						+ ", mergedSuccessCount=" + mergedSuccessCount + " error: ", e);
			}
		}

		return mergedFlag;
	}
}
