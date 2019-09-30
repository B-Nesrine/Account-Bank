package quanteam.sg.kata.bankaccount.adapters.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import quanteam.sg.kata.bankaccount.domain.exception.AccountNotFoundException;
import quanteam.sg.kata.bankaccount.domain.exception.OperationDeniedException;
import quanteam.sg.kata.bankaccount.domain.model.out.Error;

@ControllerAdvice
public class AccountInterceptorController {

    @ExceptionHandler(value = AccountNotFoundException.class)
    protected ResponseEntity<Error> handleAccountNotFoundException(AccountNotFoundException e) {
        Error error = new Error(404,"account not found");
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OperationDeniedException.class)
    protected ResponseEntity<Error> handleAccountNotFoundException(OperationDeniedException e) {
        Error error = new Error(400,"operation denied");
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
