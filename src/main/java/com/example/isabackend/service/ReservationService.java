package com.example.isabackend.service;

import com.example.isabackend.model.Reservation;
import com.example.isabackend.model.User;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

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

    public Reservation save(Reservation exam) throws IOException, WriterException, MessagingException {
        // First, save the reservation to the database to get the generated ID
        Reservation savedReservation = reservationRepository.save(exam);

        // Now, the savedReservation object should have an ID
        byte[] qrCode = this.generateQRCodeImage("http://localhost:5173/reservation/" + savedReservation.getId(), 300, 300);
        this.sendReservationMail(savedReservation, qrCode);

        // Return the saved reservation
        return savedReservation;
    }


    public Reservation getReservationById(Integer id){
        Reservation reservation = this.reservationRepository.findById(id).orElseGet(null);
        return reservation;
    }
}
