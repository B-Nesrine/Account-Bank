package quanteam.sg.kata.bankaccount.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import quanteam.sg.kata.bankaccount.domain.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findById(long id);
}
