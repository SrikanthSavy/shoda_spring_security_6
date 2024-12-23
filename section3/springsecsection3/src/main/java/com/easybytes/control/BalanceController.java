package com.easybytes.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    @GetMapping("/myBalance")
    public String getBalanceDetails()
    {
        return "Here is th eBalance Dtails from DB!";
    }
}
