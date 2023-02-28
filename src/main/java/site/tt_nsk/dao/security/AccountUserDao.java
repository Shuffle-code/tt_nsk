package site.tt_nsk.dao.security;

import site.tt_nsk.entity.security.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByUsername(String username);

//    @Override
//    long count();

//@Query(value = "SELECT player_image.path FROM player_image WHERE player_image.player_id = :id LIMIT 1", nativeQuery = true)
//String findImageNameByPlayerId(@Param("id") Long id);
//UPDATE nsk_tt.user_role t
//    SET t.ROLE_ID = 3
//    WHERE t.USER_ID = 14
//    AND t.ROLE_ID = 2;
}
