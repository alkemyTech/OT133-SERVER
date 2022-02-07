package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import java.util.List;

public interface MemberService {

  MemberDTO findById(String id);

  MemberDTO update (String id, MemberDTO memberDTO);

  void delete(String id);
  
  List<MemberDTO> listAllMember();

  MemberDTO save(MemberDTO memberDTO);
}
