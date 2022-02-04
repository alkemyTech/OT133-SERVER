package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;

public interface MemberService {

  MemberDTO findById(String id);

  MemberDTO update (String id);

  void delete(String id);

}
