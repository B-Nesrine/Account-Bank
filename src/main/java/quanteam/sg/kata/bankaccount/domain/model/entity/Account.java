package quanteam.sg.kata.bankaccount.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private long id;
    private double balance;
}
