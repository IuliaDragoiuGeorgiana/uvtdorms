 package com.uvtdorms.repository.dto.response;


import java.time.LocalDateTime;

import com.uvtdorms.repository.entity.enums.StatusLaundry;

import lombok.Data;


@Data

 public class StudentLaundryAppointmentsDto {
     private StatusLaundry statusLaundry;
     private String washingMachineNumber;
     private String dryerNumber;
     private LocalDateTime intervalBeginDate;
}