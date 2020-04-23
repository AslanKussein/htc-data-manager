package kz.dilau.htcdatamanager.web.rest.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ApiResponse {

    private boolean success;
    private int code;
    private Object data;

    public static ResponseEntity RESULT(HttpStatus status, Object data, boolean success) {
        ApiResponse res = new ApiResponse();
        res.setSuccess(success);
        res.setCode(status.value());
        res.setData(data);
        return ResponseEntity.status(status).body(res);
    }

    public static ResponseEntity OK(Object data) {
        return RESULT(HttpStatus.OK, data, true);
    }

    public static ResponseEntity NO_CONTENT() {
        return NO_CONTENT(null);
    }

    public static ResponseEntity NO_CONTENT(Object data) {
        return RESULT(HttpStatus.NO_CONTENT, data, true);
    }


    public static ResponseEntity NOT_FOUND() {
        return NOT_FOUND("Not Found");
    }

    public static ResponseEntity NOT_FOUND(Object data) {
        return RESULT(HttpStatus.NOT_FOUND, data, false);
    }

    public static ResponseEntity NOT_ACCEPTABLE() {
        return NOT_ACCEPTABLE(null);
    }

    public static ResponseEntity NOT_ACCEPTABLE(Object data) {
        return RESULT(HttpStatus.NOT_ACCEPTABLE, data, false);
    }

    public static ResponseEntity BAD_REQUEST() {
        return BAD_REQUEST("Something went wrong");
    }

    public static ResponseEntity BAD_REQUEST(Object data) {
        return RESULT(HttpStatus.BAD_REQUEST, data, false);
    }
}
