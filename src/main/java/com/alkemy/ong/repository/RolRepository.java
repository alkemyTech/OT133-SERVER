package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

    Rol findByName(Roles rolUser);
}
