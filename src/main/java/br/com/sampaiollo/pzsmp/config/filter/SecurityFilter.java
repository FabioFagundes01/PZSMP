package br.com.sampaiollo.pzsmp.config.filter;

import br.com.sampaiollo.pzsmp.repository.UsuarioRepository;
import br.com.sampaiollo.pzsmp.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Tenta recuperar o token do header da requisição
        var token = this.recoverToken(request);

        // 2. Se um token foi encontrado...
        if (token != null) {
            // 3. Valida o token e extrai o login (subject) de dentro dele
            var login = tokenService.validarToken(token);
            
            // 4. Com o login, busca os detalhes do usuário no banco de dados
            UserDetails user = usuarioRepository.findByLogin(login).orElse(null);

            // 5. Se o usuário for encontrado...
            if (user != null) {
                // 6. Cria um objeto de autenticação para o Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                
                // 7. Salva essa autenticação no contexto de segurança da requisição atual
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // 8. Continua a cadeia de filtros, permitindo que a requisição prossiga
        filterChain.doFilter(request, response);
    }

    /**
     * Método auxiliar para extrair o token do Header "Authorization".
     * Ele espera o formato "Bearer <token>".
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}