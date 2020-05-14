package com.etz.merchanttransactionsync.model.payoutletnet;

import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name = "p_transactions", catalog = "webdb",schema = "")
@XmlRootElement
@EqualsAndHashCode(callSuper = false)
@ToString
public class PayOutletTransactionLog  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "TRANS_DATE", nullable = false)
    protected LocalDateTime transDate;
    @Column(name = "TRANS_OWNER", length = 25)
    private Character transOwner;
    @Column( name = "TRANS_AMOUNT")
    private Double transAmount;
    @Column(name = "TRANS_TYPE", length = 10)
    private String transType;
    @Column(name = "TRANS_REMARK", length = 255)
    private String transRemark;
    @Column(name = "TRANS_CURRENCY", length = 6)
    private String transCurrency;
    @Column( name = "TRANS_FEE")
    private Double transFee;
    @Column(name = "TRANS_ALIAS", length = 55)
    private String transAlias;
    @Column(name = "SERVICE_ID", length = 120)
    private String serviceId;
    @Column(name = "PAYBILL_PRODUCT_CODE", length = 128)
    private String payBillProductCode;
    @Column(name = "TRANS_MERCHANT_CODE", length = 24)
    private String transMerchantCode;
    @Column(name = "SENDMONEY_RECIPIENT_TYPE", length = 8)
    private String sendMoneyRecipientType;
    @Column(name = "SENDMONEY_ACCOUNT_TYPE", length = 8)
    private String sendMoneyAcctType;
    @Column(name = "TRANS_MERCHANT_NAME", length = 120)
    private String transMerchantName;
    @Column(name = "SENDMONEY_RECIPIENT_ISSUER", length = 6)
    private String sendMoneyRecipientIssuer;
    @Size(max = 11)
    @Column(name = "TRANS_STATUS", length = 11)
    private Integer transStatus;
    @Size(max = 12)
    @Column(name = "DEBIT_STATUS", length = 12)
    private String debitStatus;
    @Column(name = "WC_REF", length = 129)
    private String wcRef;
    @Size(max = 11)
    @Column(name = "VAS_VALUE", length = 11)
    private Integer vasValue;

}