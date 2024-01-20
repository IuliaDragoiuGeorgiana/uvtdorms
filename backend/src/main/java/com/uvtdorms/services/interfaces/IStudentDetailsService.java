package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.DormIdDto;

public interface IStudentDetailsService {

 public DormIdDto getStudentDormId(String email) throws Exception;
}
