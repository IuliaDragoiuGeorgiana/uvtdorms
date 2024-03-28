package com.uvtdorms.services.interfaces;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.dto.response.DryerDto;

import java.util.List;

public interface IDryerService {
    public List<DryerDto> getDryerFromDorm(String dormId) throws AppException;
}
