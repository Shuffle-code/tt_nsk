package com.example.tt_nsk.config;

import com.example.tt_nsk.entity.security.AccountUser;
import com.example.tt_nsk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Artem Kropotov
 * created at 19.06.2022
 **/
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        AccountUser accountUser = userService.findByUsername(username);
        HttpSession session = request.getSession();
        session.setAttribute("user", accountUser);
        if (!request.getHeader("referer").contains("login")) {
            response.sendRedirect(request.getHeader("referer"));
        } else {
            response.sendRedirect(request.getContextPath() + "/player/all");
        }
    }
}
