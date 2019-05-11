package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.Card;
import com.sjsu.project.cmpe202.model.Payment;
import com.sjsu.project.cmpe202.model.User;
import com.sjsu.project.cmpe202.repository.CardRepository;
import com.sjsu.project.cmpe202.repository.PaymentRepository;
import com.sjsu.project.cmpe202.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardRepository cardRepository;

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_payments_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(paymentRepository.findPaymentsByUser(user.get("user_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_payments_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Payment>> getPaymentsByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int id = userRepository.findUserByUsername(user.get("username")).getId();
        return new ResponseEntity<>(paymentRepository.findPaymentsByUser(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_payments_by_card_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Payment>> getPaymentsByCardId(@RequestBody Map<String, Integer> card) {
        if (!card.containsKey("card_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(paymentRepository.findPaymentsByCard(card.get("card_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_payments_by_card_number",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Payment>> getPaymentsByCardNumber(@RequestBody Map<String, String> card) {
        if (!card.containsKey("card_number"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int cardId = cardRepository.findCardByCardNumber(card.get("card_number")).getId();
        return new ResponseEntity<>(paymentRepository.findPaymentsByCard(cardId), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_payment_by_payment_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<Payment> getPaymentByPaymentId(@RequestBody Map<String, Integer> payment) {
        if (!payment.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(paymentRepository.findPaymentById(payment.get("payment_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/add_new_payment",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addNewPayment(@RequestBody Map<String, String> parameter) {
        if (!parameter.containsKey("username"))
            return new ResponseEntity<>("Missing parameter username", HttpStatus.BAD_REQUEST);

        if (!parameter.containsKey("card_number"))
            return new ResponseEntity<>("Missing parameter card_number", HttpStatus.BAD_REQUEST);

        if (!parameter.containsKey("total"))
            return new ResponseEntity<>("Missing parameter total", HttpStatus.BAD_REQUEST);

        Card card = cardRepository.findCardByCardNumber(parameter.get("card_number"));
        User user = userRepository.findUserByUsername(parameter.get("username"));
        Payment payment = new Payment();
        payment.setCard(card);
        payment.setUser(user);
        Double total = Double.parseDouble(parameter.get("total"));
        payment.setTotal(total);
        card.setBalance(card.getBalance() - total);
        cardRepository.save(card);
        paymentRepository.save(payment);
        return new ResponseEntity<>("Create new Payment Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/payment/get_all_payments",
            method = RequestMethod.GET,
            consumes = "application/json")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentRepository.findAll(), HttpStatus.OK);
    }
}
