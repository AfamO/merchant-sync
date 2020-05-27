package com.etz.merchanttransactionsync.model.syncdb;

import com.etz.merchanttransactionsync.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 *
 * @author stephen.obi
 */
@SuperBuilder
@Data
@Entity
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name = "oauth_client_details", catalog = "trans_sync_db",schema = "")
public class ClientMerchantDetails extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "client_id", nullable = false, length = 256)
    private String clientId;
    
    @Size(max = 256)
    @Column(name = "merchant_name", length = 256)
    private String merchantName ;
    @Size(max = 256)
    @Column(name = "emails", length = 256)
    private String emails ;
    @Temporal(TIMESTAMP)
    @Column(name = "last_modified_date", nullable = false)
    protected Date lastModifiedDate;
    @Temporal(TIMESTAMP)
    @Column(name = "last_synced_time", nullable = false)
    protected Date lastSyncedTime;
    @Size(max = 256)
    @Column(name = "resource_ids", length = 256)
    private String resourceIds;

    @Size(max = 256)
    @Column(name = "client_secret", length = 256)
    private String clientSecret;
    @Size(max = 256)
    @Column(name = "scope", length = 256)
    private String scope;
    @Size(max = 256)
    @Column(name = "authorized_grant_types", length = 256)
    private String authorizedGrantTypes;
    @Size(max = 256)
    @Column(name = "web_server_redirect_uri", length = 256)
    private String webServerRedirectUri;
    @Size(max = 256)
    @Column(name = "authorities", length = 256)
    private String authorities;
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;
    @Size(max = 4000)
    @Column(name = "additional_information", length = 4000)
    private String additionalInformation;
    @Size(max = 256)
    @Column(name = "autoapprove", length = 256)
    private String autoapprove;
}
