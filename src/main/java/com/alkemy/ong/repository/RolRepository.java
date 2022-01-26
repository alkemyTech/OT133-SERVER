package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    Optional<Rol> findByName(ERole name);

}
