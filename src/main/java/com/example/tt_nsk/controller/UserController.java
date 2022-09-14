package com.example.tt_nsk.controller;


import com.example.tt_nsk.dao.security.AccountRoleDao;
import com.example.tt_nsk.dao.security.ConfirmationCodeDao;
import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.Tour;
import com.example.tt_nsk.entity.enums.Status;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.ConfirmationCode;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PlayerImageService playerImageService;
//    private static UserDto thisUser;
//    private final AccountRoleDao accountRoleDao;
//
//
//
    @GetMapping
    public String userPage(Model model, Principal principal) {
        Player player;
        AccountUser accountUser = userService.findByUsername(principal.getName());
        player = accountUser.getPlayer();
        model.addAttribute("accountUser", accountUser);
        List<Long> imagesId = new ArrayList<>(playerImageService.uploadMultipleFiles(player.getId()));
        model.addAttribute("playerImagesId", imagesId);
        model.addAttribute("player", player);
        return "auth/user-info";
    }

//    @GetMapping("/participate")
//    public String changeStatus(Model model, Principal principal, HttpSession httpSession) {
//        Tour tour = new Tour();
//        Player player;
//        AccountUser accountUser = userService.findByUsername(principal.getName());
////        Optional<AccountUser> byUsername = accountUserDao.findByUsername(principal.getName());
////        AccountUser accountUser = byUsername.get();
////        AccountUser accountUser = changeRole(principal);
//        player = accountUser.getPlayer();
//        player.setStatus(Status.ACTIVE);
//        playerService.save(player);
//        model.addAttribute("playersTour", playerService.findAllActiveSortedByRating());
//        httpSession.setAttribute("countPlaying", playerService.countPlaying());
//        model.addAttribute("tour", tour);
//        return "tour/tour-form";
//    }

//
//    @GetMapping("/update")
//    public String registrationPage(Model model) {
////        captchaController.getCaptcha();
////        captchaGenerator.generateCaptcha();
//        UserDto userDto = new UserDto();
//        model.addAttribute("userDto", userDto);
//        return "auth/registration-form";
//    }
//
//
//    @PostMapping("/update")
//    public String handleRegistrationForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) throws IOException {
//        if (bindingResult.hasErrors()) {
//            return "auth/registration-form";
//        }
//        final String username = userDto.getUsername();
//        try {
//            userService.findByUsername(username);
//            model.addAttribute("userDto", userDto);
//            model.addAttribute("registrationError", "Username already exists");
//            return "auth/registration-form";
//        } catch (UsernameNotFoundException ignored) {}
//        thisUser = userService.register(userDto);
//        model.addAttribute("username", username);
//        String confirmationCode = userService.getConfirmationCode();
//        System.out.println("Confirmation code! " + confirmationCode);
//        userService.generateConfirmationCode(thisUser, confirmationCode);
//        model.addAttribute("id", thisUser.getId());
//        return "player/player-list";
//    }

}
