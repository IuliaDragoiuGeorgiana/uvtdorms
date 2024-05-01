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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

        private boolean studentAlreadyHasAppointmentForThisWeek(StudentDetails student,
                        LocalDateTime intervalBeginDate) {
                LocalDateTime startOfWeek = intervalBeginDate.with(DayOfWeek.MONDAY);
                LocalDateTime endOfWeek = startOfWeek.plusDays(7);

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<LaundryAppointment> cq = cb.createQuery(LaundryAppointment.class);
                Root<LaundryAppointment> appointment = cq.from(LaundryAppointment.class);

                Predicate studentPredicate = cb.equal(appointment.get("student"), student);
                Predicate datePredicate = cb.greaterThanOrEqualTo(appointment.get("intervalBeginDate"), startOfWeek);
                Predicate endDatePredicate = cb.lessThanOrEqualTo(appointment.get("intervalBeginDate"), endOfWeek);

                cq.where(studentPredicate, datePredicate, endDatePredicate);

                List<LaundryAppointment> appointments = entityManager.createQuery(cq).getResultList();

                return !appointments.isEmpty();
        }

        public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto,
                        String studentEmail)
                        throws AppException {
                User user = userRepository.getByEmail(studentEmail)
                                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

                StudentDetails student = studentDetailsRepository.findByUser(user)
                                .orElseThrow(() -> new AppException("The user is not a student",
                                                HttpStatus.BAD_REQUEST));

                if (studentAlreadyHasAppointmentForThisWeek(student,
                                createLaundryAppointmentDto.selectedDate()
                                                .atTime(createLaundryAppointmentDto.selectedInterval(), 0))) {
                        throw new AppException("The student already has an appointment for this week",
                                        HttpStatus.BAD_REQUEST);
                }

                // UUID machineUuid = createLaundryAppointmentDto.selectedMachineId();
                // if (machineUuid == null) {
                // throw new AppException("Wrong UUID", HttpStatus.BAD_REQUEST);
                // }

                WashingMachine washingMachine = washingMachineRepository
                                .findById(createLaundryAppointmentDto.selectedMachineId())
                                .orElseThrow(() -> new AppException("Washing machine not found", HttpStatus.NOT_FOUND));

                // UUID dryerUuid = createLaundryAppointmentDto.selectedDryerId();
                // if (dryerUuid == null) {
                // throw new AppException("Wrong UUID", HttpStatus.BAD_REQUEST);
                // }

                Dryer dryer = dryerRepository.findById(createLaundryAppointmentDto.selectedDryerId())
                                .orElseThrow(() -> new AppException("Dryer not found", HttpStatus.NOT_FOUND));

                LocalDateTime intervalBeginDate = createLaundryAppointmentDto.selectedDate()
                                .atTime(createLaundryAppointmentDto.selectedInterval(), 0);
                LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student,
                                washingMachine, dryer);
                laundryAppointmentRepository.save(laundryAppointment);
        }

        @Transactional
        public FreeIntervalsDto getFreeIntervalsForCreatingAppointment(GetFreeIntervalDto freeIntervalDto) {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<LaundryAppointment> cq = cb.createQuery(LaundryAppointment.class);
                Root<LaundryAppointment> appointment = cq.from(LaundryAppointment.class);

                LocalDateTime startOfDay = freeIntervalDto.getDate().atStartOfDay();
                LocalDateTime endOfDay = freeIntervalDto.getDate().atTime(23, 59, 59);

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
                List<Integer> freeHours = new ArrayList<>();
                List<Integer> occupiedHours = new ArrayList<>();

                for (LaundryAppointment appointment : appointments) {
                        occupiedHours.add(appointment.getIntervalBeginDate().getHour());
                }

                for (int i = 8; i <= 20; i += 2) {
                        if (!occupiedHours.contains(i)) {
                                freeHours.add(i);
                        }
                }

                return freeHours;
        }
}
