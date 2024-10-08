package Projects.Distributed.URL.repository;


import Projects.Distributed.URL.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URL_REPO extends JpaRepository<URL, String> {

}
