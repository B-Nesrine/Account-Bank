package quanteam.sg.kata.bankaccount.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;

import java.util.List;

public interface AccountStatementRepository extends JpaRepository<AccountStatement,Long> {
    List<AccountStatement> findByAccountId(long id);
}
