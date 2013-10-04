package tw.org.itri.ccma.css.safebox.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import tw.org.itri.ccma.css.safebox.model.walrus.EventLog;

public interface EventLogRepository extends PagingAndSortingRepository<EventLog, Long> {

	@Query("SELECT log FROM EventLog log WHERE log.actionDate >= :dateStart and log.actionDate < :dateEnd")
	public List<EventLog> findByDate(@Param("dateStart") Date date_start, @Param("dateEnd") Date date_end,
			Pageable page_info);

	@Query("SELECT log FROM EventLog log WHERE log.actionUser = :actionUser and log.resultStatus='true'")
	public Page<EventLog> findAllByMember(@Param("actionUser") String action_user, Pageable page_info);
}
