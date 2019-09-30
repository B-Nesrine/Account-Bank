package quanteam.sg.kata.bankaccount.adapters.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanteam.sg.kata.bankaccount.domain.handler.AccountManager;
import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;
import quanteam.sg.kata.bankaccount.domain.model.in.TransactionRequest;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    AccountManager accountManager;

    public AccountController(@Autowired AccountManager accountManager){
        this.accountManager = accountManager;
    }

    @PostMapping("deposit/{id}")
    public ResponseEntity<AccountStatement> deposit(@PathVariable("id") long id, @RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(this.accountManager.deposit(id,transactionRequest), HttpStatus.CREATED);
    }

    @PostMapping("withdrawal/{id}")
    public ResponseEntity<AccountStatement> withdrawal(@PathVariable("id") long id, @RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(this.accountManager.withdrawal(id,transactionRequest), HttpStatus.CREATED);
    }

    @GetMapping("history/{id}")
    public ResponseEntity<List<AccountStatement>> printHistory(@PathVariable long id){
        return new ResponseEntity<>(this.accountManager.getHistory(id),HttpStatus.OK);
    }
}
