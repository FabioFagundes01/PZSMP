package br.com.sampaiollo.pzsmp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario implements UserDetails { // Implementa UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @Column(length = 255, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    // MÉTODOS DA INTERFACE UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipo == TipoUsuario.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ou adicione sua lógica
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ou adicione sua lógica
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ou adicione sua lógica
    }

    @Override
    public boolean isEnabled() {
        return true; // Ou adicione sua lógica
    }
}