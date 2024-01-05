package com.uvtdorms.utils;

import com.uvtdorms.repository.*;
import com.uvtdorms.repository.entity.*;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader implements CommandLineRunner {
    private final ILaundryAppointmentRepository laundryAppointmentRepository;
    private final IDormRepository dormRepository;
    // private final IAnnouncementRepository announcementRepository;
    private final IDryerRepository dryerRepository;
    // private  final IRepairTicketRepository repairTicketRepository;
    private final IRoomRepository roomRepository;
    private final IStudentDetailsRepository studentDetailsRepository;
    private final IUserRepository userRepository;
    // private final   IUserRolesRepository userRolesRepository;
    private final IWashingMachineRepository washingMachineRepository;
    private final IDormAdministratorDetails dormAdministratorDetails;

    private List<String> dormsNamesList = Arrays.asList("C13", "C12");
    private List<String> roomsNamesList = Arrays.asList("127","128");

    public InitialDataLoader(
            ILaundryAppointmentRepository laundryAppointmentRepository,
            IDormRepository dormRepository,
            // IAnnouncementRepository announcementRepository,
            IDryerRepository dryerRepository,
            // IRepairTicketRepository repairTicketRepository,
            IRoomRepository roomRepository,
            IStudentDetailsRepository studentDetailsRepository,
            IUserRepository userRepository,
            // IUserRolesRepository userRolesRepository,
            IWashingMachineRepository washingMachineRepository,
            IDormAdministratorDetails dormAdministratorDetails)
    {
        this.laundryAppointmentRepository = laundryAppointmentRepository;
        this.dormRepository = dormRepository;
        // this.announcementRepository = announcementRepository;
        this.dryerRepository = dryerRepository;
        // this.repairTicketRepository = repairTicketRepository;
        this.roomRepository = roomRepository;
        this.studentDetailsRepository = studentDetailsRepository;
        this.userRepository = userRepository;
        // this.userRolesRepository = userRolesRepository;
        this.washingMachineRepository = washingMachineRepository;
        this.dormAdministratorDetails = dormAdministratorDetails;
    }

    private void initializeDorms(){
        List<String> adressesNamesList= Arrays.asList("F.C. Ripesnsia", "Studentilor");
        for(int i=0;i<dormsNamesList.size();i++){
            Dorm dorm = new Dorm();
            dorm.setDormName(dormsNamesList.get(i));
            dorm.setAddress(adressesNamesList.get(i));
            dormRepository.save(dorm);
        }
    }

    private void initializeRooms(){
        for(int i=0;i<roomsNamesList.size();i++)
        {
            Optional<Dorm> dorm=dormRepository.getByDormName(dormsNamesList.get(i));
            if(dorm.isPresent()){
                Room room= new Room();
                room.setRoomNumber(roomsNamesList.get(i));
                room.setDorm(dorm.get());
                roomRepository.save(room);
            }
        }
    }

    private void initializeStudents(){
        Optional<Room> room =roomRepository.getRoomByRoomNumber(roomsNamesList.get(0));
        if(room.isPresent()){
            User user = new User("Iulia","Dragoiu","iulia.dragoiu02@e-uvt.ro","0729616799","iuliad", Role.STUDENT,Boolean.TRUE);
            userRepository.save(user);
            StudentDetails student=new StudentDetails("6020416203228","I3183",user,room.get());
            studentDetailsRepository.save(student);
        }
    }

    private void initializeDormsAdministrators(){
        Optional<Dorm> dorm= dormRepository.getByDormName(dormsNamesList.get(0));
        if(dorm.isPresent()){
            User user= new User("Iulia123","Dragoiu123","iulia.dragiu02123@e-uvt.ro","07295540479","iuliad123", Role.ADMINISTRATOR,Boolean.TRUE);
            userRepository.save(user);
            System.out.println(dorm);
            DormAdministratorDetails administrator = new DormAdministratorDetails(user,dorm.get());
            dormAdministratorDetails.save(administrator);
        }
    }

    private void initializeMachinesAndDryers(){
        List<String> washingMachinesNames = Arrays.asList("Machine1", "Machine2");
        List<String> dryersNames = Arrays.asList("Dryer1", "Dryer2");
        for(String dormsName:dormsNamesList) {
            Optional<Dorm> dorm= dormRepository.getByDormName(dormsName);
            if(dorm.isPresent()){
                for(String washingMachineName:washingMachinesNames){
                    WashingMachine washingMachine=new WashingMachine(washingMachineName,dorm.get(), StatusMachine.FUNCTIONAL);
                    washingMachineRepository.save(washingMachine);
                }
                for(String dryerName:dryersNames){
                    Dryer dryer=new Dryer(dryerName,dorm.get(),StatusMachine.FUNCTIONAL);
                    dryerRepository.save(dryer);
                }
            }
        }
    }

    private void initializeAppointments(){
        Optional<User> user = userRepository.getByEmail("iulia.dragoiu02@e-uvt.ro");
        if(user.isEmpty()) return;

        Optional<StudentDetails> student = studentDetailsRepository.findByUser(user.get());
        if(student.isEmpty()) return;

        Optional<Dorm> dorm = dormRepository.getByDormName(dormsNamesList.get(0));
        if(dorm.isEmpty()) return;

        List<WashingMachine> washingMachines = washingMachineRepository.findByDorm(dorm.get());
        List<Dryer> dryers = dryerRepository.findByDorm(dorm.get());

        for(int i = 0; i < washingMachines.size() && i < dryers.size(); i++)
        {
            LocalDateTime intervalBeginDate = LocalDate.now().plusDays(1).atTime(8,0);
            LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student.get(), washingMachines.get(i), dryers.get(i));
            laundryAppointmentRepository.save(laundryAppointment);
        }
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        initializeDorms();
        initializeRooms();
        initializeStudents();
        initializeDormsAdministrators();
        initializeMachinesAndDryers();
        initializeAppointments();
    }
}
