package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.Organization;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {


  @Query("SELECT p.name, p.image, p.phone, p.address, p.contact FROM Organization p join p.contact")
  public Iterable<Organization> readAllDefined();

  public boolean existsByEmail(String email);
  
  @Query("SELECT p FROM Organization p WHERE p.email=?1")
  public Optional<Organization> findByEmail(String email);

  @Query("SELECT o FROM Organization o ORDER BY o.id DESC")
  List<Organization> findTopById();

  public Organization findByName(String name);

}
