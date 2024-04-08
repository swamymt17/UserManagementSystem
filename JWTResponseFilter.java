package com.ums.config;

import com.ums.entity.AppUser;
import com.ums.repository.AppUserRepository;
import com.ums.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTResponseFilter extends OncePerRequestFilter {
    private AppUserRepository userRepository;
    private JWTService jwtService;

    public JWTResponseFilter(AppUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String username = jwtService.getUserName(token);

            Optional<AppUser> opUser =userRepository.findByUsername(username);
            if(opUser.isPresent()){
                AppUser appUser = opUser.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(appUser,null,new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
           }

            //to track current user logged in
        }
        filterChain.doFilter(request,response);
        }

}
