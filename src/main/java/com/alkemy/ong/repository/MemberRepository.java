package com.alkemy.ong.repository;

import com.alkemy.ong.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
