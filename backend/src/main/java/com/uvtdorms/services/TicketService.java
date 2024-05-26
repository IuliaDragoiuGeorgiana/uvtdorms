package com.uvtdorms.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.ITicketRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.CreateTicketDto;
import com.uvtdorms.repository.dto.response.TicketDto;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.Ticket;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.enums.StatusTicket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final ITicketRepository ticketRepository;
  private final IUserRepository userRepository;
  private final IStudentDetailsRepository studentDetailsRepository;
  private final ModelMapper modelMapper;

  public void createTicket(String email, CreateTicketDto createTicketDto) {
    User user = userRepository.getByEmail(email)
        .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

    StudentDetails student = studentDetailsRepository.findByUser(user)
        .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));

    Ticket ticket = Ticket.builder()
        .title(createTicketDto.title())
        .description(createTicketDto.description())
        .tipInterventie(createTicketDto.tipInterventie())
        .statusTicket(StatusTicket.OPEN)
        .creationDate(LocalDateTime.now())
        .alreadyAnuncement(createTicketDto.alreadyAnuncement())
        .dorm(student.getRoom().getDorm())
        .student(student)
        .build();

    ticketRepository.save(ticket);
  }

  public List<TicketDto> getTicketsFromDorm(String email) {
    User user = userRepository.getByEmail(email)
        .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

    DormAdministratorDetails dormAdministrator = user.getDormAdministratorDetails();

    if (dormAdministrator == null) {
      throw new AppException("User is not a dorm administrator", HttpStatus.FORBIDDEN);
    }

    List<Ticket> tickets = ticketRepository.findByDorm(dormAdministrator.getDorm());

    return tickets.stream().map(ticket -> modelMapper.map(ticket, TicketDto.class)).collect(Collectors.toList());
  }
}