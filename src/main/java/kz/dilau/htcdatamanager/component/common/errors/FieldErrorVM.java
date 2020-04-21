package kz.dilau.htcdatamanager.component.common.errors;

import lombok.Value;

import java.io.Serializable;

@Value
public class FieldErrorVM implements Serializable {
    static long serialVersionUID = 1L;
    String objectName;
    String field;
    String message;
}
