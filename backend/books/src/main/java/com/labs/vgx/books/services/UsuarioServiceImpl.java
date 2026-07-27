package com.labs.vgx.books.services;

import com.labs.vgx.books.exceptions.BadRequestException;
import com.labs.vgx.books.exceptions.UsuarioCadastradoException;
import com.labs.vgx.books.models.UsuarioApiRequest;
import com.labs.vgx.books.models.entityes.UsuarioEntity;
import com.labs.vgx.books.repositories.IUsuarioRepository;
import com.labs.vgx.books.utils.BookUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final BookUtil bookUtil;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, BookUtil bookUtil, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bookUtil = bookUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void criandoUsuario(UsuarioApiRequest usuarioApiRequest) {

        if(!this.bookUtil.checkDataNullAndEmpty(usuarioApiRequest.getEmail())) {
            throw new BadRequestException("E-mail do usuário obrigatório.");
        }
        if(!this.bookUtil.checkDataNullAndEmpty(usuarioApiRequest.getUserName())) {
            throw new BadRequestException("Nome do usuário obrigatório.");
        }
        if(!this.bookUtil.checkDataNullAndEmpty(usuarioApiRequest.getPassword())) {
            throw new BadRequestException("Senha do usuário obrigatória.");
        }

        if(this.usuarioRepository.findByEmail(usuarioApiRequest.getEmail()).isPresent()) {
            throw new UsuarioCadastradoException("E-mail já cadastrado.");
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .guid(this.bookUtil.generateGuid())
                .email(usuarioApiRequest.getEmail())
                .userName(usuarioApiRequest.getUserName())
                .password(this.passwordEncoder.encode(usuarioApiRequest.getPassword()))
                .build();

        this.usuarioRepository.save(usuarioEntity);

    }
}
