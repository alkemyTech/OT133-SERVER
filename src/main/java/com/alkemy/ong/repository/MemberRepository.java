package com.alkemy.ong.repository;

import java.util.List;
import com.alkemy.ong.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {

  List<Member> findAll();

  void deleteSoftById(String id);
  // Metodo creado ya que si no genera error y no corre el proyecto

  public Member findByName(String name);

  @Query("SELECT M.name, M.socialLinks FROM Member M JOIN M.socialLinks")
  public List<Member> findSocialLinksForAll();

}
