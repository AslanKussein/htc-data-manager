package kz.dilau.htcdatamanager.exception;

import kz.dilau.htcdatamanager.web.dto.common.LocaledValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class SecurityException extends DetailedException {
    public SecurityException(LocaledValue description) {
        super(HttpStatus.FORBIDDEN, description);
        log.error(description.toString());
    }
}
