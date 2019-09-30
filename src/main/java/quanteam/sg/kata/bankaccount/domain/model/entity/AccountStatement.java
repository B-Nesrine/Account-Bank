package quanteam.sg.kata.bankaccount.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import quanteam.sg.kata.bankaccount.domain.model.OperationType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AccountStatement {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long operationId;
    private OperationType type;
    @CreationTimestamp
    private LocalDateTime date;
    private double amount;
    private double balance;
    @ManyToOne
    @JsonIgnore
    private Account account;
}
