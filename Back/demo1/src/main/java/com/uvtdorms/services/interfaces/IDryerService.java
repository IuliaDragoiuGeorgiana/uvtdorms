package com.uvtdorms.services.interfaces;


import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.entity.Dryer;

import java.util.List;

public interface IDryerService {
    public List<DryerDto> getDryerFromDorm(String dormId) throws Exception;
}
