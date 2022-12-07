package com.IFutureTestTask.MiniBank.controller;

import com.IFutureTestTask.MiniBank.service.impl.BalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping(value = "/balance")
public class BalanceController {

    @Autowired
    BalanceServiceImpl balanceServiceImpl;

    @RequestMapping(value ="/getBalance", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBalance(@RequestParam(name = "userId") Long userId) {
        Optional<Long> amount = balanceServiceImpl.getBalance(userId);
        //if (amount == null) return new ResponseEntity<>("User is undefined", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(amount.get(), HttpStatus.OK);
    }

    @RequestMapping(value ="/changeBalance", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeBalance(@RequestParam(name = "userId") Long userId, @RequestParam(name = "amount") Long newAmount) {
        //if (balanceServiceImpl.getBalance(userId) == null) return new ResponseEntity<>("User is undefined", HttpStatus.BAD_REQUEST);
        balanceServiceImpl.changeBalance(userId, newAmount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
