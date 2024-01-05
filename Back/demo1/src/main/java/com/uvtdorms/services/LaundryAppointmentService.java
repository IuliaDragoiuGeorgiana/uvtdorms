package com.uvtdorms.services;

import com.uvtdorms.exception.DryerNotFoundException;
import com.uvtdorms.exception.StudentNotFoundException;
import com.uvtdorms.exception.UserNotFoundException;
import com.uvtdorms.exception.WashingMachineNotFoundException;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.FreeIntervalDto;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.ILaundryAppointmentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaundryAppointmentService implements ILaundryAppointmentService {
    private final IUserRepository userRepository;
    private final IWashingMachineRepository washingMachineRepository;

    @Autowired
    private IDryerRepository dryerRepository;

    private final ILaundryAppointmentRepository laundryAppointmentRepository;

    @Autowired
    private IStudentDetailsRepository studentDetailsRepository;

    @Autowired
    private EntityManager entityManager;

    public LaundryAppointmentService(IUserRepository userRepository,
                                     ILaundryAppointmentRepository laundryAppointmentRepository,
                                    //  IDryerRepository dryerRepository,
                                     IWashingMachineRepository washingMachineRepository) {
        this.userRepository = userRepository;
        this.laundryAppointmentRepository = laundryAppointmentRepository;
        this.washingMachineRepository = washingMachineRepository;
        // this.dryerRepository = dryerRepository;
    }

    @Override
    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws Exception{

        // TODO change user to studentDetails in dto
        Optional<User> user = userRepository.getByEmail(createLaundryAppointmentDto.getUserEmail());
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }

        Optional<StudentDetails> student = studentDetailsRepository.findByUser(user.get());
        if(student.isEmpty())
        {
            throw new StudentNotFoundException();
        }

        Optional<WashingMachine> washingMachine = washingMachineRepository.findById(createLaundryAppointmentDto.getSelectedMachineId());
        if(washingMachine.isEmpty()){
            throw new WashingMachineNotFoundException();
        }
        Optional<Dryer> dryer = dryerRepository.findById(createLaundryAppointmentDto.getSelectedDryerId());
        if(dryer.isEmpty()){
            throw new DryerNotFoundException();
        }

        LocalDateTime intervalBeginDate = createLaundryAppointmentDto.getSelectedDate().atTime(createLaundryAppointmentDto.getSelectedInterval(),0);
        LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student.get(),washingMachine.get(),dryer.get());
        laundryAppointmentRepository.save(laundryAppointment);
    }

    @Override
    public List<Integer> getFreeIntervalsForCreatingAppointment(FreeIntervalDto freeIntervalDto) throws Exception
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LaundryAppointment> cq = cb.createQuery(LaundryAppointment.class);
        Root<LaundryAppointment> appointment = cq.from(LaundryAppointment.class);

        LocalDateTime startOfDay = freeIntervalDto.getDate().atStartOfDay();
        LocalDateTime endOfDay = freeIntervalDto.getDate().atTime(23, 59, 59);

        // List<Predicate> predicates = new ArrayList<>();
        // predicates.add(cb.equal(appointment.get("washMachine").get("id"), UUID.fromString(freeIntervalDto.getWashingMachineId())));
        // predicates.add(cb.equal(appointment.get("dryer").get("id"), UUID.fromString(freeIntervalDto.getDryerId())));
        // predicates.add(cb.equal(appointment.get("student").get("room").get("dorm").get("dormId"), UUID.fromString(freeIntervalDto.getDormId())));
        // predicates.add(cb.greaterThanOrEqualTo(appointment.get("intervalBeginDate"), startOfDay));
        // predicates.add(cb.lessThanOrEqualTo(appointment.get("intervalBeginDate"), endOfDay));

        Predicate dormPredicate = cb.equal(appointment.get("student").get("room").get("dorm").get("dormId"), UUID.fromString(freeIntervalDto.getDormId()));
        Predicate datePredicate = cb.greaterThanOrEqualTo(appointment.get("intervalBeginDate"), startOfDay);
        Predicate endDatePredicate = cb.lessThanOrEqualTo(appointment.get("intervalBeginDate"), endOfDay);

        // New predicates for washing machine and dryer with OR condition
        Predicate washMachinePredicate = cb.equal(appointment.get("washMachine").get("id"), UUID.fromString(freeIntervalDto.getWashingMachineId()));
        Predicate dryerPredicate = cb.equal(appointment.get("dryer").get("id"), UUID.fromString(freeIntervalDto.getDryerId()));
        Predicate machineOrDryerPredicate = cb.or(washMachinePredicate, dryerPredicate);

        // Combine all predicates
        cq.where(dormPredicate, datePredicate, endDatePredicate, machineOrDryerPredicate);

        // cq.where(predicates.toArray(new Predicate[0]));

        List<LaundryAppointment> appointments = entityManager.createQuery(cq).getResultList();

        return calculateFreeIntervals(appointments, freeIntervalDto);
    }

    private List<Integer> calculateFreeIntervals(List<LaundryAppointment> appointments, FreeIntervalDto freeIntervalDto) {
        List<Integer> freeHours = new ArrayList<>();
        List<Integer> occupiedHours = new ArrayList<>();

        for(LaundryAppointment appointment : appointments)
        {
            occupiedHours.add(appointment.getIntervalBeginDate().getHour());
        }

        for(int i = 8; i <= 18; i += 2)
        {
            if(!occupiedHours.contains(i))
            {
                freeHours.add(i);
            }
        }

        return freeHours;
    }

}
