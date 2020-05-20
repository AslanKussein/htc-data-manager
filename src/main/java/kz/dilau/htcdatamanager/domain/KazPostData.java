package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.enums.KazPostDataStatus;
import lombok.*;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "kaz_post_data")
public class KazPostData {

    @Id
    private String id;
    private String value;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private KazPostDataStatus status = KazPostDataStatus.WAITING;
}