package com.example.tt_nsk.security;

import com.example.tt_nsk.dao.PlayerDao;
import com.example.tt_nsk.dao.PlayerImageDao;
import com.example.tt_nsk.dao.security.AccountRoleDao;
import com.example.tt_nsk.dao.security.AccountUserDao;
import com.example.tt_nsk.dao.security.ConfirmationCodeDao;
import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.dto.mapper.ParticipantMapper;
import com.example.tt_nsk.dto.mapper.UserMapper;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.PlayerImage;
import com.example.tt_nsk.entity.enums.Status;
import com.example.tt_nsk.entity.security.AccountRole;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.ConfirmationCode;
import com.example.tt_nsk.entity.security.enums.AccountStatus;
import com.example.tt_nsk.exception.UsernameAlreadyExistsException;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.PlayerService;
import com.example.tt_nsk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailService implements UserDetailsService, UserService {

    private final String imageName = "image104-66.jpg";
    private final AccountUserDao accountUserDao;
    private final AccountRoleDao accountRoleDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationCodeDao confirmationCodeDao;
    private final ParticipantMapper participantMapper;
//    private final PlayerDao playerDao;
    private final PlayerService playerService;
    private final PlayerImageDao playerImageDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found")
        );

    }

    @Override
    public String getConfirmationCode() {
        String confirmationCode;
        return confirmationCode = RandomStringUtils.randomAscii(8);
    }
    @Override
    public UserDto register(UserDto userDto) {
        if (accountUserDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw  new UsernameAlreadyExistsException(String.format(
                    "User with username %s already exists", userDto.getUsername()));
        }
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        Player player = addNewPlayer(accountUser);
        playerService.save(player);
        PlayerImage playerImage = addNewImage(imageName, player);
        playerImageDao.save(playerImage);
        System.out.println(playerImageDao.count(playerImage.getId()));
        System.out.println(playerImageDao.count(45L));
        AccountRole roleUser = accountRoleDao.findByName("ROLE_USER");
        accountUser.setRoles(Set.of(roleUser));
        accountUser.setStatus(AccountStatus.ACTIVE);
        accountUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        accountUser.setPlayer(player);
        AccountUser registeredAccountUser = accountUserDao.save(accountUser);
//        playerImageService.savePlayerImage(player.getId(), playerImageService.loadFile("image104-66.jpg"));
        log.debug("User with username {} was registered successfully", registeredAccountUser.getUsername());
        return userMapper.toUserDto(registeredAccountUser);
    }

    public PlayerImage addNewImage(String nameImage, Player player){
        PlayerImage playerImage = new PlayerImage();
        playerImage.setId(playerImageDao.maxId() + 1);
        playerImage.setPath(nameImage);
        playerImage.setPlayer(player);
        System.out.println(playerImageDao.count(playerImage.getId()));
        return playerImage;
    }

    public Player addNewPlayer(AccountUser accountUser){
        Player player = participantMapper.toPlayer(accountUser);
        player.setRating(BigDecimal.valueOf(500.00));
        player.setId(playerService.maxId() + 1);
        player.setStatus(Status.NOT_ACTIVE);
        return player;
    }




    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        AccountUser user = userMapper.toAccountUser(userDto);
        if (user.getId() != null) {
            accountUserDao.findById(userDto.getId()).ifPresent(
                    (p) -> {
                        user.setVersion(p.getVersion());
                        user.setStatus(p.getStatus());
                    }
            );
        }
        return userMapper.toUserDto(accountUserDao.save(user));
    }

    @Override
    public AccountUser findByUsername(String username) {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found")
        );
    }
    public AccountUser update(AccountUser accountUser) {
        if (accountUser.getId() != null) {
            accountUserDao.findById(accountUser.getId()).ifPresent(
                    (user) -> accountUser.setVersion(user.getVersion())
            );
        }
        return accountUserDao.save(accountUser);
    }
    @Override
    public void generateConfirmationCode(UserDto thisUser, String code) {
        ConfirmationCode confirmationCode = ConfirmationCode.builder().
                code(code)
                .accountUser(userMapper.toAccountUser(thisUser))
                .build();
        confirmationCodeDao.save(confirmationCode);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userMapper.toUserDto(accountUserDao.findById(id).orElse(null));
    }

    @Override
    public List<UserDto> findAll() {
        return accountUserDao.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        final AccountUser accountUser = accountUserDao.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User with id %s not found", id)
                )
        );
        disable(accountUser);
        update(accountUser);
    }

    private void enable(final AccountUser accountUser) {
        accountUser.setStatus(AccountStatus.ACTIVE);
        accountUser.setAccountNonLocked(true);
        accountUser.setAccountNonExpired(true);
        accountUser.setEnabled(true);
        accountUser.setCredentialsNonExpired(true);
    }

    private void disable(final AccountUser accountUser) {
        accountUser.setStatus(AccountStatus.DELETED);
        accountUser.setAccountNonLocked(false);
        accountUser.setAccountNonExpired(false);
        accountUser.setEnabled(false);
        accountUser.setCredentialsNonExpired(false);
    }
}
