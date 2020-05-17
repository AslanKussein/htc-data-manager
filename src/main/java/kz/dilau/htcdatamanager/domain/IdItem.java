package kz.dilau.htcdatamanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdItem implements Serializable {

    private static final long serialVersionUID = -3710257361663295564L;

    protected Long id;
}
