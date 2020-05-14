package com.etz.merchanttransactionsync.model.ecarddb;

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
@Table(name = "e_webconnectoclog")
@XmlRootElement
@EqualsAndHashCode(callSuper = false)
public class WebConnectTransactionLog  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "MERCHANT_CODE", length = 25)
    private  String merchantCode;
    @Id
    @Column(name = "UNIQUE_TRANSID", length = 50, nullable = false)
    private  String uniqueTransId;
    @Column(name = "MERCHANT_REF", length = 50)
    private  String merchantRef;
    @Column(name = "SWITCH_REF", length = 50)
    private  String switchRef;
    @Column(name = "USERCARD_NO", length = 20)
    private  String userCardNo;
    @Column( name = "TRANS_AMOUNT", precision = 18, scale = 2)
    private BigDecimal transAmount;
    @Temporal(TIMESTAMP)
    @Column(name = "TRANS_DATE", nullable = false)
    protected Date transDate;
    @Size(max = 200)
    @Column(name = "TRANS_DESCR", length = 200)
    private String transDescr;
    @Size(max = 5)
    @Column(name = "RESPONSE", length = 5)
    private String response;
    @Column(name = "CARD_TYPE", length = 22)
    private String cardType;
    @Column(name = "COMMISSION_BY", length = 3)
    private String commissionBy;
    @Column( name = "COMMISSION_AMOUNT", precision = 18, scale = 2)
    private BigDecimal commissionAmount;
    @Column(name = "SETTLED_STATUS", length = 1)
    private Character settledStatus;
    @Column(name = "responseDescription", length = 150)
    private String responseDescription;
    @Column(name = "TERMINAL_ID", length = 15)
    private String terminalId;
    @Column(name = "cardNo", length = 21)
    private String cardNo;
    @Column(name = "CARD_REGION", length = 11)
    private String cardRegion;
    @Column(name = "CURRENCY", length = 27)
    private String currency;
    @Column(name = "SENT_AMOUNT", length = 27)
    private String sentAmount;
    @Column(name = "CXS_ID", length = 100)
    private String cxsId;
    @Column(name = "CXS_ID2", length = 100)
    private String cxsId2;
    @Column(name = "paymentOptionId", length = 256)
    private String paymentOptionId;

}