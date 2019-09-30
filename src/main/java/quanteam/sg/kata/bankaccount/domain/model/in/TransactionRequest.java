package quanteam.sg.kata.bankaccount.domain.model.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private double amount;
}
