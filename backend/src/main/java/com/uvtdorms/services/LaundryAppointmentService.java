package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.GetFreeIntervalDto;
import com.uvtdorms.repository.dto.response.FreeIntervalsDto;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaundryAppointmentService {
        private final IUserRepository userRepository;
        private final IWashingMachineRepository washingMachineRepository;
        private final IDryerRepository dryerRepository;
        private final ILaundryAppointmentRepository laundryAppointmentRepository;
        private final IStudentDetailsRepository studentDetailsRepository;
        private final EntityManager entityManager;

        public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto,
                        String studentEmail)
                        throws AppException {
                User user = userRepository.getByEmail(studentEmail)
                                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

                StudentDetails student = studentDetailsRepository.findByUser(user)
                                .orElseThrow(() -> new AppException("The user is not a student",
                                                HttpStatus.BAD_REQUEST));

                UUID machineUuid = createLaundryAppointmentDto.selectedMachineId();
                if (machineUuid == null) {
                        throw new AppException("Wrong UUID", HttpStatus.BAD_REQUEST);
                }

                WashingMachine washingMachine = washingMachineRepository.findById(machineUuid)
                                .orElseThrow(() -> new AppException("Washing machine not found", HttpStatus.NOT_FOUND));

                UUID dryerUuid = createLaundryAppointmentDto.selectedDryerId();
                if (dryerUuid == null) {
                        throw new AppException("Wrong UUID", HttpStatus.BAD_REQUEST);
                }

                Dryer dryer = dryerRepository.findById(dryerUuid)
                                .orElseThrow(() -> new AppException("Dryer not found", HttpStatus.NOT_FOUND));

                LocalDateTime intervalBeginDate = createLaundryAppointmentDto.selectedDate()
                                .atTime(createLaundryAppointmentDto.selectedInterval(), 0);
                LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student,
                                washingMachine, dryer);
                laundryAppointmentRepository.save(laundryAppointment);
        }

        public FreeIntervalsDto getFreeIntervalsForCreatingAppointment(GetFreeIntervalDto freeIntervalDto) {

                System.out.println(freeIntervalDto.toString());

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<LaundryAppointment> cq = cb.createQuery(LaundryAppointment.class);
                Root<LaundryAppointment> appointment = cq.from(LaundryAppointment.class);

                LocalDateTime startOfDay = freeIntervalDto.getDate().atStartOfDay();
                LocalDateTime endOfDay = freeIntervalDto.getDate().atTime(23, 59, 59);

                System.out.println("Start of day: " + startOfDay);
                System.out.println("End of day: " + endOfDay);

                Predicate dormPredicate = cb.equal(appointment.get("student").get("room").get("dorm").get("dormId"),
                                UUID.fromString(freeIntervalDto.getDormId()));
                Predicate datePredicate = cb.greaterThanOrEqualTo(appointment.get("intervalBeginDate"), startOfDay);
                Predicate endDatePredicate = cb.lessThanOrEqualTo(appointment.get("intervalBeginDate"), endOfDay);

                Predicate washMachinePredicate = cb.equal(appointment.get("washMachine").get("id"),
                                UUID.fromString(freeIntervalDto.getWashingMachineId()));
                Predicate dryerPredicate = cb.equal(appointment.get("dryer").get("id"),
                                UUID.fromString(freeIntervalDto.getDryerId()));
                Predicate machineOrDryerPredicate = cb.or(washMachinePredicate, dryerPredicate);

                cq.where(dormPredicate, datePredicate, endDatePredicate, machineOrDryerPredicate);

                List<LaundryAppointment> appointments = entityManager.createQuery(cq).getResultList();

                return new FreeIntervalsDto(calculateFreeIntervals(appointments, freeIntervalDto));
        }

        private List<Integer> calculateFreeIntervals(List<LaundryAppointment> appointments,
                        GetFreeIntervalDto freeIntervalDto) {
                System.out.println(appointments.size());
                List<Integer> freeHours = new ArrayList<>();
                List<Integer> occupiedHours = new ArrayList<>();

                for (LaundryAppointment appointment : appointments) {
                        occupiedHours.add(appointment.getIntervalBeginDate().getHour());
                }

                System.out.println(occupiedHours.size());

                for (int i = 8; i <= 20; i += 2) {
                        if (!occupiedHours.contains(i)) {
                                freeHours.add(i);
                        }
                }

                System.out.println(freeHours.size());

                return freeHours;
        }
}
