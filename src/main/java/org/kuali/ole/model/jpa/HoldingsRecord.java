package org.kuali.ole.model.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sheiks on 27/10/16.
 */
@Entity
@Table(name="ole_ds_holdings_t")
@NamedQuery(name="HoldingsRecord.findAll", query="SELECT h FROM HoldingsRecord h")
public class HoldingsRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="HOLDINGS_ID")
    private int holdingsId;

    @Column(name="ACCESS_PASSWORD")
    private String accessPassword;

    @Column(name="ACCESS_STATUS")
    private String accessStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACCESS_STATUS_DATE_UPDATED")
    private Date accessStatusDateUpdated;

    @Column(name="ACCESS_USERNAME")
    private String accessUsername;

    @Column(name="ADMIN_PASSWORD")
    private String adminPassword;

    @Column(name="ADMIN_URL")
    private String adminUrl;

    @Column(name="ADMIN_USERNAME")
    private String adminUsername;

    @Column(name="ALLOW_ILL")
    private String allowIll;

    @Column(name="AUTHENTICATION_TYPE_ID")
    private int authenticationTypeId;

    @Column(name="CALL_NUMBER")
    private String callNumber;

    @Column(name="CALL_NUMBER_PREFIX")
    private String callNumberPrefix;

    @Column(name="CALL_NUMBER_TYPE_ID")
    private int callNumberTypeId;

    @Column(name="CANCELLATION_CANDIDATE")
    private String cancellationCandidate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CANCELLATION_DECISION_DT")
    private Date cancellationDecisionDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CANCELLATION_EFFECTIVE_DT")
    private Date cancellationEffectiveDt;

    @Column(name="CANCELLATION_REASON")
    private String cancellationReason;

    @Column(name="COPY_NUMBER")
    private String copyNumber;

    @Column(name="CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CURRENT_SBRCPTN_END_DT")
    private Date currentSbrcptnEndDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CURRENT_SBRCPTN_START_DT")
    private Date currentSbrcptnStartDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_CREATED")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_UPDATED")
    private Date dateUpdated;

    @Column(name="E_RESOURCE_ID")
    private String eResourceId;

    @Column(name="FIRST_INDICATOR")
    private String firstIndicator;

    @Column(name="FORMER_HOLDINGS_ID")
    private String formerHoldingsId;

    @Column(name="GOKB_IDENTIFIER")
    private int gokbIdentifier;

    @Column(name="HOLDINGS_TYPE")
    private String holdingsType;

    private String imprint;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INITIAL_SBRCPTN_START_DT")
    private Date initialSbrcptnStartDt;

    @Column(name="LOCAL_PERSISTENT_URI")
    private String localPersistentUri;

    private String location;

    @Column(name="LOCATION_ID")
    private int locationId;

    @Column(name="LOCATION_LEVEL")
    private String locationLevel;

    @Column(name="MATERIALS_SPECIFIED")
    private String materialsSpecified;

    @Column(name="NUMBER_SIMULT_USERS")
    private int numberSimultUsers;

    private String platform;

    @Column(name="PROXIED_RESOURCE")
    private String proxiedResource;

    private String publisher;

    @Column(name="RECEIPT_STATUS_ID")
    private int receiptStatusId;

    @Column(name="SECOND_INDICATOR")
    private String secondIndicator;

    @Column(name="SHELVING_ORDER")
    private String shelvingOrder;

    @Lob
    @Column(name="SOURCE_HOLDINGS_CONTENT")
    private String sourceHoldingsContent;

    @Column(name="STAFF_ONLY")
    private String staffOnly;

    @Column(name="SUBSCRIPTION_STATUS")
    private String subscriptionStatus;

    @Column(name="UNIQUE_ID_PREFIX")
    private String uniqueIdPrefix;

    @Column(name="UPDATED_BY")
    private String updatedBy;

    //bi-directional many-to-one association to BibRecord
    @ManyToOne
    @JoinColumn(name="BIB_ID")
    private BibRecord oleDsBibT;

    public HoldingsRecord() {
    }

    public int getHoldingsId() {
        return this.holdingsId;
    }

    public void setHoldingsId(int holdingsId) {
        this.holdingsId = holdingsId;
    }

    public String getAccessPassword() {
        return this.accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public String getAccessStatus() {
        return this.accessStatus;
    }

    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Date getAccessStatusDateUpdated() {
        return this.accessStatusDateUpdated;
    }

    public void setAccessStatusDateUpdated(Date accessStatusDateUpdated) {
        this.accessStatusDateUpdated = accessStatusDateUpdated;
    }

    public String getAccessUsername() {
        return this.accessUsername;
    }

    public void setAccessUsername(String accessUsername) {
        this.accessUsername = accessUsername;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminUrl() {
        return this.adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getAdminUsername() {
        return this.adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAllowIll() {
        return this.allowIll;
    }

    public void setAllowIll(String allowIll) {
        this.allowIll = allowIll;
    }

    public int getAuthenticationTypeId() {
        return this.authenticationTypeId;
    }

    public void setAuthenticationTypeId(int authenticationTypeId) {
        this.authenticationTypeId = authenticationTypeId;
    }

    public String getCallNumber() {
        return this.callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallNumberPrefix() {
        return this.callNumberPrefix;
    }

    public void setCallNumberPrefix(String callNumberPrefix) {
        this.callNumberPrefix = callNumberPrefix;
    }

    public int getCallNumberTypeId() {
        return this.callNumberTypeId;
    }

    public void setCallNumberTypeId(int callNumberTypeId) {
        this.callNumberTypeId = callNumberTypeId;
    }

    public String getCancellationCandidate() {
        return this.cancellationCandidate;
    }

    public void setCancellationCandidate(String cancellationCandidate) {
        this.cancellationCandidate = cancellationCandidate;
    }

    public Date getCancellationDecisionDt() {
        return this.cancellationDecisionDt;
    }

    public void setCancellationDecisionDt(Date cancellationDecisionDt) {
        this.cancellationDecisionDt = cancellationDecisionDt;
    }

    public Date getCancellationEffectiveDt() {
        return this.cancellationEffectiveDt;
    }

    public void setCancellationEffectiveDt(Date cancellationEffectiveDt) {
        this.cancellationEffectiveDt = cancellationEffectiveDt;
    }

    public String getCancellationReason() {
        return this.cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCopyNumber() {
        return this.copyNumber;
    }

    public void setCopyNumber(String copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCurrentSbrcptnEndDt() {
        return this.currentSbrcptnEndDt;
    }

    public void setCurrentSbrcptnEndDt(Date currentSbrcptnEndDt) {
        this.currentSbrcptnEndDt = currentSbrcptnEndDt;
    }

    public Date getCurrentSbrcptnStartDt() {
        return this.currentSbrcptnStartDt;
    }

    public void setCurrentSbrcptnStartDt(Date currentSbrcptnStartDt) {
        this.currentSbrcptnStartDt = currentSbrcptnStartDt;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getEResourceId() {
        return this.eResourceId;
    }

    public void setEResourceId(String eResourceId) {
        this.eResourceId = eResourceId;
    }

    public String getFirstIndicator() {
        return this.firstIndicator;
    }

    public void setFirstIndicator(String firstIndicator) {
        this.firstIndicator = firstIndicator;
    }

    public String getFormerHoldingsId() {
        return this.formerHoldingsId;
    }

    public void setFormerHoldingsId(String formerHoldingsId) {
        this.formerHoldingsId = formerHoldingsId;
    }

    public int getGokbIdentifier() {
        return this.gokbIdentifier;
    }

    public void setGokbIdentifier(int gokbIdentifier) {
        this.gokbIdentifier = gokbIdentifier;
    }

    public String getHoldingsType() {
        return this.holdingsType;
    }

    public void setHoldingsType(String holdingsType) {
        this.holdingsType = holdingsType;
    }

    public String getImprint() {
        return this.imprint;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    public Date getInitialSbrcptnStartDt() {
        return this.initialSbrcptnStartDt;
    }

    public void setInitialSbrcptnStartDt(Date initialSbrcptnStartDt) {
        this.initialSbrcptnStartDt = initialSbrcptnStartDt;
    }

    public String getLocalPersistentUri() {
        return this.localPersistentUri;
    }

    public void setLocalPersistentUri(String localPersistentUri) {
        this.localPersistentUri = localPersistentUri;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationId() {
        return this.locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationLevel() {
        return this.locationLevel;
    }

    public void setLocationLevel(String locationLevel) {
        this.locationLevel = locationLevel;
    }

    public String getMaterialsSpecified() {
        return this.materialsSpecified;
    }

    public void setMaterialsSpecified(String materialsSpecified) {
        this.materialsSpecified = materialsSpecified;
    }

    public int getNumberSimultUsers() {
        return this.numberSimultUsers;
    }

    public void setNumberSimultUsers(int numberSimultUsers) {
        this.numberSimultUsers = numberSimultUsers;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProxiedResource() {
        return this.proxiedResource;
    }

    public void setProxiedResource(String proxiedResource) {
        this.proxiedResource = proxiedResource;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getReceiptStatusId() {
        return this.receiptStatusId;
    }

    public void setReceiptStatusId(int receiptStatusId) {
        this.receiptStatusId = receiptStatusId;
    }

    public String getSecondIndicator() {
        return this.secondIndicator;
    }

    public void setSecondIndicator(String secondIndicator) {
        this.secondIndicator = secondIndicator;
    }

    public String getShelvingOrder() {
        return this.shelvingOrder;
    }

    public void setShelvingOrder(String shelvingOrder) {
        this.shelvingOrder = shelvingOrder;
    }

    public String getSourceHoldingsContent() {
        return this.sourceHoldingsContent;
    }

    public void setSourceHoldingsContent(String sourceHoldingsContent) {
        this.sourceHoldingsContent = sourceHoldingsContent;
    }

    public String getStaffOnly() {
        return this.staffOnly;
    }

    public void setStaffOnly(String staffOnly) {
        this.staffOnly = staffOnly;
    }

    public String getSubscriptionStatus() {
        return this.subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getUniqueIdPrefix() {
        return this.uniqueIdPrefix;
    }

    public void setUniqueIdPrefix(String uniqueIdPrefix) {
        this.uniqueIdPrefix = uniqueIdPrefix;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public BibRecord getOleDsBibT() {
        return this.oleDsBibT;
    }

    public void setOleDsBibT(BibRecord oleDsBibT) {
        this.oleDsBibT = oleDsBibT;
    }

}