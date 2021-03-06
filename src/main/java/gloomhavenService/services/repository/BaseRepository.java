package gloomhavenService.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
