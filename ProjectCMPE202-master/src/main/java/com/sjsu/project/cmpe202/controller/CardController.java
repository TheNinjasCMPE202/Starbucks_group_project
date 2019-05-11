package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.Card;
import com.sjsu.project.cmpe202.model.User;
import com.sjsu.project.cmpe202.repository.CardRepository;
import com.sjsu.project.cmpe202.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;


    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/get_card_by_card_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<Card> getCardByCardId(@RequestBody Map<String, Integer> card) {
        if (!card.containsKey("card_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cardRepository.findCardById(card.get("card_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/get_cards_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Card>> getCardsByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cardRepository.findCardsByUser(user.get("user_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/get_cards_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<Card>> getCardsByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int id = userRepository.findUserByUsername(user.get("username")).getId();
        return new ResponseEntity<>(cardRepository.findCardsByUser(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/add_new_card",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addNewCard(@RequestBody Map<String, String> card) {
        User user = userRepository.findUserByUsername(card.get("username"));
        Double balance = Double.parseDouble(card.get("balance"));
        Card cardInstance = new Card(card.get("card_number"), card.get("card_code"), balance, user);
        cardRepository.save(cardInstance);

        return new ResponseEntity<>("Add New Card successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/delete_card_by_card_id",
            method = RequestMethod.DELETE,
            consumes = "application/json")
    public ResponseEntity<String> deleteCardByCardId(@RequestBody Map<String, Integer> card) {
        if (!card.containsKey("card_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cardRepository.deleteById(card.get("card_id"));
        return new ResponseEntity<>("Delete Card Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/delete_card_by_card_number",
            method = RequestMethod.DELETE,
            consumes = "application/json")
    public ResponseEntity<String> deleteCardByCardNumber(@RequestBody Map<String, String> card) {
        if (!card.containsKey("card_number"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        int id = cardRepository.findCardByCardNumber(card.get("card_number")).getId();
        cardRepository.deleteById(id);
        return new ResponseEntity<>("Delete Card Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/update_card_by_card_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> updateCardByCardId(@RequestBody Map<String, String> card) {
        if (!card.containsKey("card_id"))
            return new ResponseEntity<>("Missing Parameter card_id", HttpStatus.BAD_REQUEST);

        if (!card.containsKey("balance"))
            return new ResponseEntity<>("Missing Parameter balance", HttpStatus.BAD_REQUEST);

        Integer id = Integer.parseInt(card.get("card_id"));
        Double balance = Double.parseDouble(card.get("balance"));
        Card newCard = cardRepository.findCardById(id);
        newCard.setBalance(balance);
        cardRepository.save(newCard);
        return new ResponseEntity<>("Update Card Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/update_card_by_card_number",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> updateCardByCardNumber(@RequestBody Map<String, String> card) {
        if (!card.containsKey("card_number"))
            return new ResponseEntity<>("Missing Parameter card_number", HttpStatus.BAD_REQUEST);

        if (!card.containsKey("balance"))
            return new ResponseEntity<>("Missing Parameter balance", HttpStatus.BAD_REQUEST);

        Double balance = Double.parseDouble(card.get("balance"));
        Card newCard = cardRepository.findCardByCardNumber(card.get("card_number"));
        newCard.setBalance(balance);
        cardRepository.save(newCard);
        return new ResponseEntity<>("Update Card Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @RequestMapping(
            value = "/card/get_all_cards",
            method = RequestMethod.GET,
            consumes = "application/json")
    public ResponseEntity<List<Card>> getAllCards() {
        return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
    }
}
