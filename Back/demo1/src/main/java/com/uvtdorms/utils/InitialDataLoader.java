package com.uvtdorms.utils;

import com.uvtdorms.repository.*;
import com.uvtdorms.repository.entity.*;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader implements CommandLineRunner {
    private final ILaundryAppointmentRepository iLaundryAppointmentRepository;
    private final IDormRepository iDormRepository;
    private final IAnnouncementRepository iAnnouncementRepository;
    private final IDryerRepository iDryerRepository;
    private  final IRepairTicketRepository iRepairTicketRepository;
    private final IRoomRepository iRoomRepository;
    private final IStudentDetailsRepository iStudentDetailsRepository;
    private final IUserRepository iUserRepository;
    private final   IUserRolesRepository iUserRolesRepository;
    private final IWashingMachineRepository iWashingMachineRepository;
    private final IDormAdministratorDetails iDormAdministratorDetails;

    private List<String> dormsNamesList = Arrays.asList("C13", "C12") ;

    private List<String> roomsNamesList = Arrays.asList("127","128") ;

    public InitialDataLoader(ILaundryAppointmentRepository iLaundryAppointmentRepository, IDormRepository iDormRepository,
                             IAnnouncementRepository iAnnouncementRepository, IDryerRepository iDryerRepository, IRepairTicketRepository iRepairTicketRepository,
                             IRoomRepository iRoomRepository, IStudentDetailsRepository iStudentDetailsRepository, IUserRepository iUserRepository, IUserRolesRepository iUserRolesRepository,
                             IWashingMachineRepository iWashingMachineRepository,IDormAdministratorDetails iDormAdministratorDetails) {
        this.iLaundryAppointmentRepository = iLaundryAppointmentRepository;
        this.iDormRepository = iDormRepository;
        this.iAnnouncementRepository = iAnnouncementRepository;
        this.iDryerRepository = iDryerRepository;
        this.iRepairTicketRepository = iRepairTicketRepository;
        this.iRoomRepository = iRoomRepository;
        this.iStudentDetailsRepository = iStudentDetailsRepository;
        this.iUserRepository = iUserRepository;
        this.iUserRolesRepository = iUserRolesRepository;
        this.iWashingMachineRepository = iWashingMachineRepository;
        this.iDormAdministratorDetails = iDormAdministratorDetails;
    }
    private void initializeDorms(){
        List<String> adressesNamesList= Arrays.asList("F.C. Ripesnsia", "Studentilor");
        for(int i=0;i<dormsNamesList.size();i++){
            Dorm dorm = new Dorm();
            dorm.setDormName(dormsNamesList.get(i));
            dorm.setAddress(adressesNamesList.get(i));
            iDormRepository.save(dorm);
        }

    }
    private void initializeRooms(){

        for(int i=0;i<roomsNamesList.size();i++)
        {
            Optional<Dorm> dorm=iDormRepository.getByDormName(dormsNamesList.get(i));
            if(dorm.isPresent()){
                Room room= new Room();
                room.setRoomNumber(roomsNamesList.get(i));
                room.setDorm(dorm.get());
                iRoomRepository.save(room);
            }

        }
    }
    private void initializeStudents(){

        Optional<Room> room =iRoomRepository.getRoomByRoomNumber(roomsNamesList.get(0));
        if(room.isPresent()){
            User user = new User("Iulia","Dragoiu","iulia.dragiu02@e-uvt.ro","0729616799","iuliad", Role.STUDENT,Boolean.TRUE);
            iUserRepository.save(user);
            StudentDetails student=new StudentDetails("6020416203228","I3183",user,room.get());
            iStudentDetailsRepository.save(student);

        }

    }
    private void initializeDormsAdministrators(){
        Optional<Dorm> dorm= iDormRepository.getByDormName(dormsNamesList.get(0));
        if(dorm.isPresent()){
            User user= new User("Iulia123","Dragoiu123","iulia.dragiu02123@e-uvt.ro","07295540479","iuliad123", Role.ADMINISTRATOR,Boolean.TRUE);
            iUserRepository.save(user);
            System.out.println(dorm);
            DormAdministratorDetails administrator = new DormAdministratorDetails(user,dorm.get());
            iDormAdministratorDetails.save(administrator);



        }


    }
    private void initializeMachinesAndDryers(){
        List<String> washingMachinesNames = Arrays.asList("Machine1", "Machine2");
        List<String> dryersNames = Arrays.asList("Dryer1", "Dryer2");
        for(String dormsName:dormsNamesList) {
            Optional<Dorm> dorm= iDormRepository.getByDormName(dormsName);
            if(dorm.isPresent()){
                for(String washingMachineName:washingMachinesNames){
                    WashingMachine washingMachine=new WashingMachine(washingMachineName,dorm.get(), StatusMachine.FUNCTIONAL);
                    iWashingMachineRepository.save(washingMachine);
                }
                for(String dryerName:dryersNames){
                    Dryer dryer=new Dryer(dryerName,dorm.get(),StatusMachine.FUNCTIONAL);
                    iDryerRepository.save(dryer);
                }


            }
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

    }
    }

