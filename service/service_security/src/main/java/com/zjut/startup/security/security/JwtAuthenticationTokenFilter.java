package com.zjut.startup.security.security;

import com.sun.javafx.fxml.BeanAdapter;
import com.zjut.startup.security.config.JwtSecurityProperties;
import com.zjut.startup.security.utils.JwtTokenUtils;
import com.zjut.startup.security.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api("实现token验证的过滤器")
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * 该类继承OncePerRequestFilter，顾名思义，它能够确保在一次请求中只通过一次filter
     * 该类使用JwtTokenUtils工具类进行token校验
     */

    private JwtTokenUtils jwtTokenUtils;
    //private BeanAdapter SpringContextHolder;

    public JwtAuthenticationTokenFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        JwtSecurityProperties jwtSecurityProperties = SpringContextHolder.getBean(JwtSecurityProperties.class);
        String requestRri = httpServletRequest.getRequestURI();
        //获取request token
        String token = null;
        String bearerToken = httpServletRequest.getHeader(jwtSecurityProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtSecurityProperties.getTokenStartWith())) {
            token = bearerToken.substring(jwtSecurityProperties.getTokenStartWith().length());
        }

        if (StringUtils.hasText(token) && jwtTokenUtils.validateToken(token)) {
            Authentication authentication = jwtTokenUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
        } else {
            log.debug("no valid JWT token found, uri: {}", requestRri);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}



