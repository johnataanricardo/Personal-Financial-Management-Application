package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UserRepository userRepository;

    @Autowired
    public UsuarioService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Usuario> showAllUsers(){
       return (List<Usuario>) userRepository.findAll();
    }

    public List<UsuarioDto> showAllUsersDto(){
        List<UsuarioDto> usuarioDtos = new LinkedList<>();
        showAllUsers().forEach( usuario -> {
            usuarioDtos.add(convertUsuarioDto(usuario));
        });
        return usuarioDtos;
    }

    public Optional<Usuario> findUserByUsernameEmail(String usernameEmail){
        return  Optional.ofNullable(this.userRepository.findByEmail(usernameEmail));
    }

    public Optional<Usuario> findUsuarioById(Long id){
        return userRepository.findById(id);
    }

    public Usuario newUser(Usuario usuario){
       return userRepository.save(usuario);
    }

    public void removeUser(Long id){
        userRepository.deleteById(id);
    }

    private UsuarioDto convertUsuarioDto(Usuario usuario) {
        return  new UsuarioDto(String.valueOf(usuario.getId()),
                usuario.getNome(),
                usuario.getEmail());
    }

}
