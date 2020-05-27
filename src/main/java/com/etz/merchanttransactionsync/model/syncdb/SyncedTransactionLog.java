package com.etz.merchanttransactionsync.model.syncdb;

import com.etz.merchanttransactionsync.model.ecarddb.*;
import com.etz.merchanttransactionsync.model.Auditable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sync_tb", catalog = "trans_sync_db",schema = "")
@XmlRootElement
@EqualsAndHashCode(callSuper = false)
public class SyncedTransactionLog  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private  Long id;
    @Column(name = "product_name", length = 30)
    private  String productName;
    @Column(name = "merchant_name", length = 50)
    private  String merchantName;
    @Column(name = "terminal_id_merchant_code", length = 11)
    private  String terminalIdMerchantCode;
    @Column( name = "status", length = 10)
    private String syncedStatus;
    @Column(name = "last_sync_time", nullable = false)
    protected Date lastSyncedTime;

}