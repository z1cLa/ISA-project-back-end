package com.example.isabackend.service;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Equipment;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.AppointmentRepository;
import com.example.isabackend.repository.ReservationRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendReservationMail(Reservation reservation, byte[] attachment) throws MessagingException {
        String subject = "Reservation details";
        String mailContent = "<p> Dear " + reservation.getUser().getFirstName() + ", </p>";
        mailContent += "<p> Please scan the QR code below to check details of your registration: </p>";
        mailContent += "<p>Thank you <br> ISA Group 7</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        helper.addAttachment("QRCode.png", new ByteArrayResource(attachment));

        try{
            helper.setTo(reservation.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    public List<Reservation> getUserReservations(Integer id){
        return this.reservationRepository.getUserReservations(id);
    }

    public List<Reservation> getCompanyReservations(Integer id){
        return this.reservationRepository.getCompanyReservations(id);
    }

    @Transactional
    public Reservation save(Reservation exam) throws IOException, WriterException, MessagingException {
        Appointment appointment = appointmentService.findById(exam.getAppointment().getId());

        synchronized (appointment) {
            if (appointment.getIsReserved()) {
                return null; // Appointment already reserved, decline the request
            }

            try {
                // Attempt to save the reservation
                Reservation savedReservation = reservationRepository.save(exam);

                // Update appointment to mark it as reserved
                appointmentService.updateWhenReserved(appointment);

                // Generate QR code and send email
                byte[] qrCode = this.generateQRCodeImage("http://localhost:5173/reservation/" + savedReservation.getId(), 300, 300);
                this.sendReservationMail(savedReservation, qrCode);

                // Return the saved reservation
                return savedReservation;
            } catch (PessimisticLockingFailureException | DataIntegrityViolationException e) {
                // Handle locking failure or unique constraint violation
                return null;
            }
        }
    }

    public void cancelReservation(Integer id, Integer points){
        this.appointmentService.updateWhenCanceled(this.reservationRepository.findById(id).get().getAppointment(), points);
        this.reservationRepository.delete(this.reservationRepository.findById(id).get());
    }


    public Reservation getReservationById(Integer id){
        Reservation reservation = this.reservationRepository.findById(id).orElseGet(null);
        return reservation;
    }

    public List<Reservation> getFinishedReservationsByUserId(Integer userId) {
        return reservationRepository.findByUserIdAndStatus(userId, "finished");
    }

    public int getPriceForReservation(Integer reservationId){
        Set<Equipment> equipment = reservationRepository.findEquipmentsByReservationId(reservationId);
        List<Equipment> equipmentList = new ArrayList<>(equipment);
        int sumOfPrice=0;
        for (Equipment equip : equipmentList) {
            sumOfPrice = sumOfPrice+equip.getEquipmentPrice();
        }
        return sumOfPrice;
    }

}
