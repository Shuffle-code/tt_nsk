package com.example.tt_nsk.controller;


import com.example.tt_nsk.entity.Player;
import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.service.PlayerImageService;
import com.example.tt_nsk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
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

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('player.read') || isAnonymous()")
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(playerImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

}
