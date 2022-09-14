package com.example.tt_nsk.controller;


import com.example.tt_nsk.dao.security.AccountRoleDao;
import com.example.tt_nsk.dao.security.ConfirmationCodeDao;
import com.example.tt_nsk.dto.UserDto;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.entity.security.ConfirmationCode;
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

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private static UserDto thisUser;
    private final AccountRoleDao accountRoleDao;



    @GetMapping("/login")
    public String loginPage() {
        return "auth/login-form";
    }

    @GetMapping("/update")
    public String registrationPage(Model model) {
//        captchaController.getCaptcha();
//        captchaGenerator.generateCaptcha();
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "auth/registration-form";
    }


    @PostMapping("/update")
    public String handleRegistrationForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "auth/registration-form";
        }
        final String username = userDto.getUsername();
        try {
            userService.findByUsername(username);
            model.addAttribute("userDto", userDto);
            model.addAttribute("registrationError", "Username already exists");
            return "auth/registration-form";
        } catch (UsernameNotFoundException ignored) {}
        thisUser = userService.register(userDto);
        model.addAttribute("username", username);
        String confirmationCode = userService.getConfirmationCode();
        System.out.println("Confirmation code! " + confirmationCode);
        userService.generateConfirmationCode(thisUser, confirmationCode);
        model.addAttribute("id", thisUser.getId());
        return "player/player-list";
    }

//    @PostMapping("/confirmation")
//    public String handleConfirmationForm (String code, Model model) {
//        model.addAttribute("code", code);
//        System.out.println("code: " + code);
//        ConfirmationCode confirmationCodeBy_id = confirmationCodeDao.findConfirmationCodeByAccountUser_Id(thisUser.getId());
////        System.out.println(confirmationCodeBy_id.getCode() + " + from model: " + code);
////        System.out.println("from model: " + code);
//        if (confirmationCodeBy_id.equals(code)) {
//            System.out.println(confirmationCodeBy_id.equals(code));
//            AccountUser accountUser = confirmationCodeBy_id.getAccountUser();
//            accountUser.setEnabled(true);
//            accountUser.setAccountNonLocked(true);
//            accountUser.setCredentialsNonExpired(true);
//            accountUser.setAccountNonExpired(true);
//            userService.update(accountUser);
//            return "redirect:/auth/login";
//        }
////        System.out.println(!code.equals(confirmationCodeBy_id.toString()));
//        return "auth/registration-confirmation";
//    }

//    @GetMapping("/participate")
//    public String changeStatus(Model model, Principal principal) {
//        AccountRole roleUser = accountRoleDao.findByName("ROLE_PLAYER");
//        p
//        confirmationCodeBy_id.getAccountUser();
//        Optional<AccountUser> byUsername = accountUserDao.findByUsername(principal.getName());
//        AccountUser accountUser = byUsername.get();
//        accountUser.setRoles(Set.of(roleUser));
//        userService.update(accountUser);
//        System.out.println(principal.getName());
////        model.addAttribute("players", playerService.findAllSortedByRating());
//        return "player/players-list";
//    }

}
