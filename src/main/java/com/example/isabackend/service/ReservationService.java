package com.example.isabackend.service;

import com.example.isabackend.model.Reservation;
import com.example.isabackend.model.User;
import com.example.isabackend.repository.AppointmentRepository;
import com.example.isabackend.repository.ReservationRepository;
import com.example.isabackend.repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserRepository userRepository;

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
        return this.reservationRepository.getUserInProgressReservations(id);
    }

    public List<Reservation> getCompanyReservations(Integer id){
        return this.reservationRepository.getCompanyReservations(id);
    }

    public Reservation save(Reservation exam) throws IOException, WriterException, MessagingException {
        // First, save the reservation to the database to get the generated ID
        Reservation savedReservation = reservationRepository.save(exam);

        // Now, the savedReservation object should have an ID
        byte[] qrCode = this.generateQRCodeImage("http://localhost:5173/reservation/" + savedReservation.getId(), 300, 300);
        this.sendReservationMail(savedReservation, qrCode);

        // Return the saved reservation
        return savedReservation;
    }

    public void cancelReservation(Integer id, Integer points){
        this.appointmentService.updateWhenCanceled(this.reservationRepository.findById(id).get().getAppointment(), points);
        this.reservationRepository.delete(this.reservationRepository.findById(id).get());
    }


    public Reservation getReservationById(Integer id){
        Reservation reservation = this.reservationRepository.findById(id).orElseGet(null);
        return reservation;
    }

    public List<Reservation> getInProgressReservations() {
        return reservationRepository.findByStatus("In progress");
    }

    public Optional<Reservation> finishReservation(Integer reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus("Finished");
            reservationRepository.save(reservation);
        }

        return optionalReservation;
    }

    @Transactional
    public void updateReservationStatus() {
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {

            if (!isAppointmentDateValid(reservation.getAppointment().getDate()) && !"Declined".equals(reservation.getStatus())) {
                reservation.setStatus("Declined");
                reservation.getUser().setPenaltyPoints(+2);
            }
        }
    }
    private boolean isAppointmentDateValid(Date appointmentDate) {
        Date currentDate = new Date();
        return !appointmentDate.before(currentDate);
    }


}
