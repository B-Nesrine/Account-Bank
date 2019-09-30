package quanteam.sg.kata.bankaccount.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import quanteam.sg.kata.bankaccount.adapters.persistence.AccountRepository;
import quanteam.sg.kata.bankaccount.adapters.persistence.AccountStatementRepository;
import quanteam.sg.kata.bankaccount.adapters.service.AccountManagerAdapter;
import quanteam.sg.kata.bankaccount.domain.exception.AccountNotFoundException;
import quanteam.sg.kata.bankaccount.domain.exception.OperationDeniedException;
import quanteam.sg.kata.bankaccount.domain.handler.AccountManager;
import quanteam.sg.kata.bankaccount.domain.model.OperationType;
import quanteam.sg.kata.bankaccount.domain.model.entity.Account;
import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;
import quanteam.sg.kata.bankaccount.domain.model.in.TransactionRequest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountManagerTest {

    private AccountManager accountManager;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountStatementRepository accountStatementRepository;

    @Before
    public void setUp(){
        this.accountManager = new AccountManagerAdapter(accountStatementRepository,accountRepository);
    }


    @Test
    public void shouldDepositOnSuccess(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(200);
        Account account = new Account();
        account.setId(1);
        account.setBalance(400);
        AccountStatement expectedAccountStatement = new AccountStatement();
        expectedAccountStatement.setAccount(account);
        expectedAccountStatement.setAmount(200);
        expectedAccountStatement.setBalance(600);
        expectedAccountStatement.setType(OperationType.DEPOSIT);
        when(accountRepository.findById(anyLong())).thenReturn(account);
        when(accountStatementRepository.save(any())).thenReturn(new AccountStatement());
        when(accountRepository.save(any())).thenReturn(new Account());
        AccountStatement result = accountManager.deposit(1,transactionRequest);
        assertEquals(expectedAccountStatement.getAccount().getId(),result.getAccount().getId());
        assertEquals(expectedAccountStatement.getType(),result.getType());
        assertEquals(expectedAccountStatement.getAmount(), result.getAmount(), 0.0);
        assertEquals(expectedAccountStatement.getBalance(), result.getBalance(), 0.0);
    }


    @Test(expected = AccountNotFoundException.class)
    public void shouldDepositThrowAccountNotFoundException() {
        when(accountRepository.findById(anyLong())).thenReturn(null);
        accountManager.deposit(1,new TransactionRequest());
    }

    @Test
    public void shouldWithdrawalOnSuccess(){
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(200);
        Account account = new Account();
        account.setId(1);
        account.setBalance(400);
        AccountStatement expectedAccountStatement = new AccountStatement();
        expectedAccountStatement.setAccount(account);
        expectedAccountStatement.setAmount(200);
        expectedAccountStatement.setBalance(200);
        expectedAccountStatement.setType(OperationType.WITHDRAWAL);
        when(accountRepository.findById(anyLong())).thenReturn(account);
        when(accountStatementRepository.save(any())).thenReturn(new AccountStatement());
        when(accountRepository.save(any())).thenReturn(new Account());
        AccountStatement result = accountManager.withdrawal(1,transactionRequest);
        assertEquals(expectedAccountStatement.getAccount().getId(),result.getAccount().getId());
        assertEquals(expectedAccountStatement.getType(),result.getType());
        assertEquals(expectedAccountStatement.getAmount(), result.getAmount(), 0.0);
        assertEquals(expectedAccountStatement.getBalance(), result.getBalance(), 0.0);
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldWithdrawalThrowAccountNotFoundException() {
        when(accountRepository.findById(anyLong())).thenReturn(null);
        accountManager.withdrawal(1,new TransactionRequest());
    }


    @Test(expected = OperationDeniedException.class)
    public void shouldWithdrawalThrowOperationDeniedException() {
        Account account = new Account();
        account.setBalance(400);
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(500);
        when(accountRepository.findById(anyLong())).thenReturn(account);
        accountManager.withdrawal(1,transactionRequest);
    }

    @Test
    public void shouldGetHistoryOnSuccess(){
        when(accountStatementRepository.findByAccount_Id(anyLong())).thenReturn(new ArrayList<>());
        assertTrue(accountManager.getHistory(1).isEmpty());
    }



}
