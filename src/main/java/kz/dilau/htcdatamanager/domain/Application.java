package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationSource;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "application")
public class Application extends AuditableBaseEntity<String, Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;

    @Column(name = "operation_type_id", insertable = false, updatable = false)
    private Long operationTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_type_id")
    private ObjectType objectType;
    @Column(name = "object_type_id", insertable = false, updatable = false)
    private Long objectTypeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_source_id", nullable = false)
    private ApplicationSource applicationSource;

    @Column(name = "application_source_id", insertable = false, updatable = false)
    private Long applicationSourceId;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private ApplicationSellData applicationSellData;
    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private ApplicationPurchaseData applicationPurchaseData;

    @Column(name = "client_login")
    private String clientLogin;
    @ManyToOne(optional = false)
    @JoinColumn(name = "application_status_id")
    private ApplicationStatus applicationStatus;
    @OrderBy("id")
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationStatusHistory> statusHistoryList;

    @Column(name = "current_agent")
    private String currentAgent;
    @OrderBy("id")
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Assignment> assignmentList;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationContract contract;

    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationDeposit deposit;

    @OneToOne(mappedBy = "sellApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApplicationDeposit sellDeposit;

    @Column(name = "confirm_doc_guid")
    private String confirmDocGuid;

    @Column(name = "device_uuid")
    private String deviceUuid;

    @Column(name = "view_count")
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_application_id")
    private Application targetApplication;

    public List<ApplicationStatusHistory> getStatusHistoryList() {
        if (isNull(statusHistoryList)) {
            statusHistoryList = new ArrayList<>();
        }
        return statusHistoryList;
    }

    public List<Assignment> getAssignmentList() {
        if (isNull(assignmentList)) {
            assignmentList = new ArrayList<>();
        }
        return assignmentList;
    }

    @Transient
    public ApplicationStatusHistory getLastStatusHistory() {
        return getStatusHistoryList().get(getStatusHistoryList().size() - 1);
    }

    @Transient
    public boolean isReservedRealProperty() {
        return this.operationType.isSell() && nonNull(this.applicationSellData) && nonNull(this.applicationSellData.getRealProperty())
                && nonNull(this.applicationSellData.getRealProperty().getIsReserved()) && this.applicationSellData.getRealProperty().getIsReserved();
    }
}
