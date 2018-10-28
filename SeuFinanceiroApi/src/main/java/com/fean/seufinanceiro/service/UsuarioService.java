package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.security.enums.ProfileEnum;
import com.fean.seufinanceiro.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static List<Usuario> usuarios;

    public UsuarioService(){
        criarListUsuario();
    }

    private List<Usuario> criarListUsuario(){
        if (usuarios == null) {
            usuarios = new LinkedList<>();
            usuarios.add(new Usuario(1L, "User1",
                    "Exemple",
                    "123123123",
                    "Santa Catarina",
                    "lalala",
                    "Lalalala",
                    "30",
                    "userexample@hotmail.com",
                    PasswordUtils.generateBCrypt("123123"),
                    ProfileEnum.ROLE_USUARIO));

            usuarios.add(new Usuario(2L, "User2",
                    "Example2",
                    "12312312323",
                    "Santa Catarina",
                    "lalalala",
                    "lala",
                    "27",
                    "userexample2@hotmail.com",
                    PasswordUtils.generateBCrypt("123123"),
                    ProfileEnum.ROLE_USUARIO));
        }
        return usuarios;
    }

    public void add(Usuario usuario){
        int posicao = usuarios.size();
        usuario.setId(posicao + 1L);
        usuarios.add(usuario);
    }

    public void remove(Long id){
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

    public void update(Usuario usuario){
        usuarios.stream().filter( (user) -> user.getId().equals(usuario.getId()))
                    .forEach( (user) -> {
                        user.setNome(usuario.getNome());
                        user.setSobreNome(usuario.getSobreNome());
                        user.setCpf(usuario.getCpf());
                        user.setEstado(usuario.getEstado());
                        user.setCidade(usuario.getCidade());
                        user.setNumero(usuario.getNumero());
                    });
    }

    public List<Usuario> usuarios(){ return usuarios; }

    public List<UsuarioDto> usuariosDto(){
        List<UsuarioDto> usuarioDtos = new LinkedList<>();
        usuarios.forEach( usuario -> {
            usuarioDtos.add(convertUsuarioDto(usuario));
        });
        return usuarioDtos;
    }

    public Usuario finById(Long id){
        return usuarios().stream()
                    .filter( (user) -> user.getId().equals(id))
                    .collect(Collectors.toList())
                    .get(0);
    }


    public Usuario finByEmail(String email){
        return usuarios().stream()
                .filter( (user) -> user.getEmail().equals(email))
                .collect(Collectors.toList())
                .get(0);
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
