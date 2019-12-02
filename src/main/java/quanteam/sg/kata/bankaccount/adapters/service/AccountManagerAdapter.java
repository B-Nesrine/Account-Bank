package quanteam.sg.kata.bankaccount.adapters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quanteam.sg.kata.bankaccount.adapters.persistence.AccountRepository;
import quanteam.sg.kata.bankaccount.adapters.persistence.AccountStatementRepository;
import quanteam.sg.kata.bankaccount.domain.exception.AccountNotFoundException;
import quanteam.sg.kata.bankaccount.domain.exception.OperationDeniedException;
import quanteam.sg.kata.bankaccount.domain.handler.AccountManager;
import quanteam.sg.kata.bankaccount.domain.model.OperationType;
import quanteam.sg.kata.bankaccount.domain.model.entity.Account;
import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;
import quanteam.sg.kata.bankaccount.domain.model.in.TransactionRequest;

import java.util.List;

@Service
public class AccountManagerAdapter implements AccountManager {

    private AccountStatementRepository accountStatementRepository;
    private AccountRepository accountRepository;

    public AccountManagerAdapter(@Autowired AccountStatementRepository accountStatementRepository,
                                 @Autowired AccountRepository accountRepository){
        this.accountStatementRepository = accountStatementRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountStatement deposit(long accountId, TransactionRequest transactionRequest) {
        Account account = this.accountRepository.findById(accountId);
        if(account==null){
            throw new AccountNotFoundException();
        }
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccount(account);
        accountStatement.setAmount(transactionRequest.getAmount());
        accountStatement.setBalance(account.getBalance()+transactionRequest.getAmount());
        accountStatement.setType(OperationType.DEPOSIT);
        this.accountStatementRepository.save(accountStatement);
        account.setBalance(account.getBalance()+transactionRequest.getAmount());
        this.accountRepository.save(account);
        return accountStatement;
    }

    @Override
    public AccountStatement withdrawal(long accountId, TransactionRequest transactionRequest) {
        Account account = this.accountRepository.findById(accountId);
        if(account==null){
            throw new AccountNotFoundException();
        }
        if(account.getBalance()<transactionRequest.getAmount()){
            throw new OperationDeniedException();
        }
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccount(account);
        accountStatement.setAmount(transactionRequest.getAmount());
        accountStatement.setBalance(account.getBalance()-transactionRequest.getAmount());
        accountStatement.setType(OperationType.WITHDRAWAL);
        this.accountStatementRepository.save(accountStatement);
        account.setBalance(account.getBalance()-transactionRequest.getAmount());
        this.accountRepository.save(account);
        return accountStatement;
    }

    @Override
    public List<AccountStatement> getHistory(long accountId) {
        return this.accountStatementRepository.findByAccountId(accountId);
    }
}
