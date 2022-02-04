package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;

public interface MemberService {

  MemberDTO findById(String id);

  MemberDTO update (String id, MemberDTO memberDTO);

  void delete(String id);

}
