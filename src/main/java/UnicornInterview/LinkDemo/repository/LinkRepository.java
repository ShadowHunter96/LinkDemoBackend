package UnicornInterview.LinkDemo.repository;

import UnicornInterview.LinkDemo.model.LinkEntity;
import org.springframework.data.history.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

/**
 * Created by User: Vu
 * Date: 12.04.2024
 * Time: 21:23
 */
public interface LinkRepository extends RevisionRepository<LinkEntity,Long,Integer>, JpaRepository<LinkEntity,Long> {
    List<LinkEntity> findByDeletedFalse();




}
