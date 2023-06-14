package com.proyect.apiequipo.security.jwt;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proyect.apiequipo.service.UserService;
import com.proyect.apiequipo.user.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@Log
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtTokenFromRequest(request);

        try {

            // SI NUESTRO TOKEN TIENE TEXTO Y ES VALIDO
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

                // SACAMOS EL UUID A PARTIR DEL MÉTODO QUE HEMOS IMPLEMENTADO JUSTO DEBAJO.
                UUID userId = jwtProvider.getUserIdFromJwtToken(token);

                // OBTENEMOS EL USUARIO MEDIANTE SU ID.
                Optional<User> result = userService.findById(userId);

                // SI EL RESULTADO ES POSITIVO, ES DECIR EXISTE EL USUARIO
                if (result.isPresent()) {
                    User user = result.get();

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                            null, user.getAuthorities()); // PASAMOS COMO ARGUMENTO EL USUARIO, LAS CREDENCIALES EN ESTE
                                                            // CASO SON NULAS, Y LOS PERMISOS DEL MISMO.

                    // ESTABLECEMOS LOS DETALLES DE LA PETICIÓN
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    // ESTABLECEMOS AL USUARIO COMO USUARIO AUTENTICADO
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception ex) {

            log.info("Authentication error using token JWT: " + ex.getMessage());

        }

        filterChain.doFilter(request, response);
    }
private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }

        return null;
    }

}
