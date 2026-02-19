package com.kiraliza.spring.store.clothes.controller.handler;

import com.kiraliza.spring.store.clothes.helper.LogHelper;
import com.kiraliza.spring.store.clothes.type.Role;
import com.kiraliza.spring.store.clothes.type.Routes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class RoleAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Autowired
    private Routes routes;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        LogHelper.info("=======onAuthenticationSuccess - " + authentication.getName());
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }

    protected String determineTargetUrl(Authentication authentication)
    {
        LogHelper.info("=========determineTargetUrl");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name())))
        {
            LogHelper.info("=========determineTargetUrl:ADMIN");
            return Routes.CATALOG_ROOT;
        }
        else if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Role.MANAGER.name())))
        {
            LogHelper.info("=========determineTargetUrl:MANAGER");
            return Routes.CATALOG_ROOT;
        }
        else
        {
            LogHelper.info("=========determineTargetUrl:HOME");
            return "index";
        }
    }
}