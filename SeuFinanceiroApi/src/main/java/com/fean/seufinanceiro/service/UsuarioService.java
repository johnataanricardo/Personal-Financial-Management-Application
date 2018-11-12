package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> showAllUsers(){
       return (List<Usuario>) usuarioRepository.findAll();
    }

    public List<UsuarioDto> showAllUsersDto(){
        List<UsuarioDto> usuarioDtos = new LinkedList<>();
        showAllUsers().forEach( usuario -> {
            usuarioDtos.add(convertUsuarioDto(usuario));
        });
        return usuarioDtos;
    }

    public Optional<Usuario> findUserByUsernameEmail(String usernameEmail){
        return  Optional.ofNullable(this.usuarioRepository.findByEmail(usernameEmail));
    }

    public Optional<Usuario> findUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    public void newUser(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void removeUser(Long id){
        usuarioRepository.deleteById(id);
    }

    private UsuarioDto convertUsuarioDto(Usuario usuario) {
        return  new UsuarioDto(String.valueOf(usuario.getId()),
                usuario.getNome(),
                usuario.getSobreNome(),
                usuario.getCpf(),
                usuario.getEstado(),
                usuario.getCidade(),
                usuario.getEndereco(),
                usuario.getNumero(),
                usuario.getEmail());
    }

}
