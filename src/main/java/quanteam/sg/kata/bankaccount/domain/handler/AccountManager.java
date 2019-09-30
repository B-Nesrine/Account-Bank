package quanteam.sg.kata.bankaccount.domain.handler;

import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;
import quanteam.sg.kata.bankaccount.domain.model.in.TransactionRequest;

import java.util.List;

public interface AccountManager {
    AccountStatement deposit(long accountId,TransactionRequest transactionRequest);
    AccountStatement withdrawal(long accountId,TransactionRequest transactionRequest);
    List<AccountStatement> getHistory(long accountId);
}
