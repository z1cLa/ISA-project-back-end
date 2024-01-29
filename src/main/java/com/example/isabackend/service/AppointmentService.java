package com.example.isabackend.service;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.AppointmentRepository;
import com.example.isabackend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    public Appointment findById(Integer id) { return appointmentRepository.findById(id).orElseGet(null);}

    public List<Appointment> findAll() {return appointmentRepository.findAll();}

    public Appointment save(Appointment exam)
    {
        exam.getDate().setHours(0);
        return appointmentRepository.save(exam);
    }

    public Appointment saveForSpecificAppointmen(Appointment exam)
    {
        exam.getDate().setHours(0);
        Set<User> admins = companyService.getAdminsByCompanyId(exam.getCompany().getId());
        List<User> adminList = new ArrayList<>(admins);
        int numberOfAdmins = adminList.size();
        Random random = new Random();
        int randomNumber = random.nextInt(numberOfAdmins);
        User randomAdmin = adminList.get(randomNumber);
        exam.setUser(randomAdmin);
        return appointmentRepository.save(exam);
    }

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findByCompanyIdAndIsReservedFalse(Integer companyId) {
        return appointmentRepository.findByCompanyIdAndIsCompaniesAppointmentIsTrueAndIsReservedFalse(companyId);
    }

    public Appointment updateWhenReserved(Appointment appointment) {
        if (!appointmentRepository.existsById(appointment.getId())) {
            return null;
        }
        appointment.setIsReserved(true);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return updatedAppointment;
    }

    public Appointment updateWhenCanceled(Appointment appointment, Integer points){
        if (!appointmentRepository.existsById(appointment.getId())) {
            return null;
        }
        appointment.setIsReserved(false);
        this.userService.addPenaltyPoints(appointment.getUser().getId().intValue(), points);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return updatedAppointment;
    }

    public List<Time> findFreeTimesByDateAndCompanyId(Date date, Integer companyId)
    {
        date.setHours(0);
        List<Appointment> appointments =appointmentRepository.findAllByDateAndCompany_IdAndIsReservedIsTrue(date, companyId);
        List<Appointment> appointments2 =appointmentRepository.findAllByDateAndCompany_IdAndIsCompaniesAppointmentIsTrue(date, companyId);
        for (Appointment appointment2 : appointments2)
        {
            appointments.add(appointment2);
        }
        List<Time> freeTimes = new ArrayList<>();
        Company company = companyService.findById(companyId);
        Time workStarts = company.getStartTime();
        Time workEnds = company.getEndTime();
        //Time workStarts = new java.sql.Time(0, 0, 0);
        //Time workEnds = new java.sql.Time(20, 0, 0);
        Time currentTime = workStarts;
        boolean today = isToday(date);

        while (currentTime.before(workEnds)) {
            boolean flag =true;
            for (Appointment appointment : appointments) {
                if (currentTime.equals(addHours(appointment.getTime(),appointment.getDuration())) || currentTime.equals(appointment.getTime()) || (currentTime.after(appointment.getTime()) && currentTime.before(addHours(appointment.getTime(),appointment.getDuration())))) {
                    flag = false;
                }
                Time currentTime2 =new Time(currentTime.getTime() + 3600000);
                if (currentTime2.equals(addHours(appointment.getTime(),appointment.getDuration())) || currentTime2.equals(appointment.getTime()) || (currentTime2.after(appointment.getTime()) && currentTime2.before(addHours(appointment.getTime(),appointment.getDuration())))) {
                    flag = false;
                }
            }
            if(today)
            {
                if(isBeforeNow(currentTime))
                {
                    flag = false;
                }
            }
            if(flag)
            {
                freeTimes.add(currentTime);
            }

            currentTime = new Time(currentTime.getTime() + 3600000);
        }
        return freeTimes;
    }
    private static Time addHours(Time time,Integer duration) {
        long durationLong = (long) duration;
        long currentTimeInMillis = time.getTime();
        long newTimeInMillis = currentTimeInMillis + 3600000*durationLong; // 3600000 milliseconds = 1 hour
        return new Time(newTimeInMillis);
    }

    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(date);

        return calendar.get(Calendar.YEAR) == compareCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == compareCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH) == compareCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isBeforeNow(Time time) {
        LocalTime currentTime = LocalTime.now();
        LocalTime compareTime = time.toLocalTime();

        return compareTime.isBefore(currentTime);
    }
}
