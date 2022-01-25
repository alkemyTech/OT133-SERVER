package com.alkemy.ong.repository;

import java.util.List;
import com.alkemy.ong.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {

    List<Member> findAll();

    @Query("UPDATE #{#entityName} e SET e.softDelete=true WHERE e.id=?1")
    @Modifying
    public void deleteSoftById(String id);
}
