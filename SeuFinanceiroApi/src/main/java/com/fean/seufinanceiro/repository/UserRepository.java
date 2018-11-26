package com.fean.seufinanceiro.repository;

import com.fean.seufinanceiro.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String usernameEmail);
}
