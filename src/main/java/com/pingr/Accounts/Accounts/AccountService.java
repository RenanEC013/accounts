package com.pingr.Accounts.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repo;
    private final ProducerService producer;

    @Autowired
    public AccountService(AccountRepository repo, ProducerService producer) {
        this.repo = repo;
        this.producer = producer;
    }

    //   tem ID, está salva no banco
    //     |
    //     |         não tem ID, não está no banco ainda
    //     |                                  |
    //     v                                  v
    public Account createAccount(Account account) throws IllegalArgumentException {
        try {
            Account acc = this.repo.save(account);
            this.producer.emitAccountCreatedEvent(acc);
            return acc;
        } catch(Exception e) {
            throw new IllegalArgumentException("Account creation violates restrictions" + "[account: " + account + "]");
        }
    }

    public void deleteAccount(Long id) {
        Account acc = this.repo.getById(id);
        this.repo.deleteById(id);
        this.producer.emitAccountRemovedEvent(acc);
    }

    public void replaceAccount(Account newAccount, Long id) {
        repo.findById(id)
                .map(account -> {
                    account.setUsername(newAccount.getUsername());
                    account.setEmail(newAccount.getEmail());
                    account.setPassword(newAccount.getPassword());
                    Account acc = this.repo.save(account);
                    this.producer.emitAccountUpdatedEvent(acc);
                    return acc;
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    Account acc = this.repo.save(newAccount);
                    this.producer.emitAccountUpdatedEvent(acc);
                    return acc;
                });
    }
}
