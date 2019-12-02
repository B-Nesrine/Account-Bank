package quanteam.sg.kata.bankaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quanteam.sg.kata.bankaccount.adapters.persistence.AccountRepository;
import quanteam.sg.kata.bankaccount.domain.model.entity.Account;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BankAccountApplication implements ApplicationRunner {

	/**
	 * TO CREATE OUR FIRST ACCOUNT
	 */
    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setBalance(1000);
        accountRepository.save(account);
    }
}
