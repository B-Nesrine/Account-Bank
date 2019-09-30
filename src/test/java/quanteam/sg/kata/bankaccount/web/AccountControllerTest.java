package quanteam.sg.kata.bankaccount.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import quanteam.sg.kata.bankaccount.adapters.web.AccountController;
import quanteam.sg.kata.bankaccount.domain.exception.AccountNotFoundException;
import quanteam.sg.kata.bankaccount.domain.exception.OperationDeniedException;
import quanteam.sg.kata.bankaccount.domain.handler.AccountManager;
import quanteam.sg.kata.bankaccount.domain.model.OperationType;
import quanteam.sg.kata.bankaccount.domain.model.entity.AccountStatement;
import quanteam.sg.kata.bankaccount.domain.model.in.TransactionRequest;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private AccountController accountController;
    @MockBean
    private AccountManager accountManager;

    @Before
    public void setUp(){
        this.accountController = new AccountController(accountManager);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldGetHistoryOnSuccess() throws Exception {
        when(accountManager.getHistory(anyLong())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/account/history/{id}",1)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldDepositOnSuccess() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(300);
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setType(OperationType.DEPOSIT);
        accountStatement.setAmount(300);
        when(accountManager.deposit(anyLong(),any())).thenReturn(accountStatement);
        this.mockMvc.perform(post("/account/deposit/{id}",1)
                .content(asJsonString(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(300))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldDepositThrowAccountNotFoundException() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(300);
        when(accountManager.deposit(anyLong(),any())).thenThrow(new AccountNotFoundException());
        this.mockMvc.perform(post("/account/deposit/{id}",1)
                .content(asJsonString(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("account not found"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldWithdrawalOnSuccess() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(300);
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setType(OperationType.WITHDRAWAL);
        accountStatement.setAmount(300);
        when(accountManager.withdrawal(anyLong(),any())).thenReturn(accountStatement);
        this.mockMvc.perform(post("/account/withdrawal/{id}",1)
                .content(asJsonString(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.amount").value(300))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldWithDrawalThrowOperationDeniedExceptionException() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(300);
        when(accountManager.withdrawal(anyLong(),any())).thenThrow(new OperationDeniedException());
        this.mockMvc.perform(post("/account/withdrawal/{id}",1)
                .content(asJsonString(transactionRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("operation denied"))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
