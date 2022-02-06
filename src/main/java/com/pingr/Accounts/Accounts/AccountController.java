package com.pingr.Accounts.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createOneAccount(@RequestBody Account account) {
        System.out.println(account);

        return this.service.createAccount(account);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {

        this.service.deleteAccount(id);
    }

    @PutMapping("/accounts/{id}")
    public void replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {

        this.service.replaceAccount(newAccount, id);
    }
}
