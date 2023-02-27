package site.tt_nsk.dao.security;

import site.tt_nsk.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
