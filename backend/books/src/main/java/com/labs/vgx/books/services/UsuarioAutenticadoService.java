package com.labs.vgx.books.services;

import com.labs.vgx.books.models.entityes.UsuarioEntity;
import com.labs.vgx.books.repositories.IUsuarioRepository;
import com.labs.vgx.books.security.UserDetail;
import com.labs.vgx.books.utils.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioAutenticadoService implements UserDetailsService {
    private final IUsuarioRepository iUsuarioRepository;
    private final JWTUtil jwtUtil;

    public UsuarioAutenticadoService(IUsuarioRepository iUsuarioRepository, JWTUtil jwtUtil) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetail loadUserByUsername(String email) {
        UsuarioEntity usuario = iUsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " não Encontrado!"));

        return new UserDetail(usuario.getUserName(), usuario.getEmail(), usuario.getPassword(),
                jwtUtil.setRoles());
    }
}
