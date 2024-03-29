package site.tt_nsk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends IllegalArgumentException {

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }
}
