package tw.org.itri.ccma.css.safebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.itri.ccma.css.safebox.model.walrus.EBSClientEventLog;

public interface EBSClientEventLogRepository extends JpaRepository<EBSClientEventLog, Long> {

}
