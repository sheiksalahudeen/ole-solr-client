package org.kuali.ole.model.jpa;

import org.kuali.ole.model.jpa.*;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by sheiks 10/11/16.
 */
@Entity
@Table(name="ole_ds_item_t")
@NamedQuery(name="ItemRecord.findAll", query="SELECT o FROM ItemRecord o")
public class ItemRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ITEM_ID")
	private int itemId;

	private String barcode;

	@Column(name="BARCODE_ARSL")
	private String barcodeArsl;

	@Column(name="CALL_NUMBER")
	private String callNumber;

	@Column(name="CALL_NUMBER_PREFIX")
	private String callNumberPrefix;

	@Column(name="CALL_NUMBER_TYPE_ID")
	private int callNumberTypeId;

	@Column(name="CHECK_IN_NOTE")
	private String checkInNote;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CHECK_OUT_DATE_TIME")
	private Date checkOutDateTime;

	private String chronology;

	@Column(name="CLAIMS_RETURNED")
	private String claimsReturned;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLAIMS_RETURNED_DATE_CREATED")
	private Date claimsReturnedDateCreated;

	@Column(name="CLAIMS_RETURNED_NOTE")
	private String claimsReturnedNote;

	@Column(name="COPY_NUMBER")
	private String copyNumber;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CURRENT_BORROWER")
	private String currentBorrower;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATED")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATED")
	private Date dateUpdated;

	@Column(name="DESC_OF_PIECES")
	private String descOfPieces;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DUE_DATE_TIME")
	private Date dueDateTime;

	private String enumeration;

	@Column(name="FAST_ADD")
	private String fastAdd;

	private String fund;

	@Column(name="HIGH_DENSITY_STORAGE_ID")
	private int highDensityStorageId;

	@Column(name="ITEM_DAMAGED_NOTE")
	private String itemDamagedNote;

	@Column(name="ITEM_DAMAGED_STATUS")
	private String itemDamagedStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ITEM_STATUS_DATE_UPDATED")
	private Date itemStatusDateUpdated;

	@Column(name="ITEM_STATUS_ID")
	private int itemStatusId;

	@Column(name="ITEM_TYPE_ID")
	private int itemTypeId;

	private String location;

	@Column(name="LOCATION_ID")
	private int locationId;

	@Column(name="LOCATION_LEVEL")
	private String locationLevel;

	@Column(name="MISSING_PIECES")
	private String missingPieces;

	@Column(name="MISSING_PIECES_COUNT")
	private int missingPiecesCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MISSING_PIECES_EFFECTIVE_DATE")
	private Date missingPiecesEffectiveDate;

	@Column(name="MISSING_PIECES_NOTE")
	private String missingPiecesNote;

	@Column(name="NUM_OF_RENEW")
	private int numOfRenew;

	@Column(name="NUM_PIECES")
	private String numPieces;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ORG_DUE_DATE_TIME")
	private Date orgDueDateTime;

	private BigDecimal price;

	@Column(name="PROXY_BORROWER")
	private String proxyBorrower;

	@Column(name="PURCHASE_ORDER_LINE_ITEM_ID")
	private String purchaseOrderLineItemId;

	@Column(name="SHELVING_ORDER")
	private String shelvingOrder;

	@Column(name="STAFF_ONLY")
	private String staffOnly;

	@Column(name="TEMP_ITEM_TYPE_ID")
	private int tempItemTypeId;

	@Column(name="UNIQUE_ID_PREFIX")
	private String uniqueIdPrefix;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	private String uri;

	@Column(name="VENDOR_LINE_ITEM_ID")
	private String vendorLineItemId;

	@Column(name="VOLUME_NUMBER")
	private String volumeNumber;

	//bi-directional many-to-one association to HoldingsRecord
	@ManyToOne
	@JoinColumn(name="HOLDINGS_ID")
	private HoldingsRecord holdingsRecord;

	public ItemRecord() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeArsl() {
		return this.barcodeArsl;
	}

	public void setBarcodeArsl(String barcodeArsl) {
		this.barcodeArsl = barcodeArsl;
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

	public String getCheckInNote() {
		return this.checkInNote;
	}

	public void setCheckInNote(String checkInNote) {
		this.checkInNote = checkInNote;
	}

	public Date getCheckOutDateTime() {
		return this.checkOutDateTime;
	}

	public void setCheckOutDateTime(Date checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}

	public String getChronology() {
		return this.chronology;
	}

	public void setChronology(String chronology) {
		this.chronology = chronology;
	}

	public String getClaimsReturned() {
		return this.claimsReturned;
	}

	public void setClaimsReturned(String claimsReturned) {
		this.claimsReturned = claimsReturned;
	}

	public Date getClaimsReturnedDateCreated() {
		return this.claimsReturnedDateCreated;
	}

	public void setClaimsReturnedDateCreated(Date claimsReturnedDateCreated) {
		this.claimsReturnedDateCreated = claimsReturnedDateCreated;
	}

	public String getClaimsReturnedNote() {
		return this.claimsReturnedNote;
	}

	public void setClaimsReturnedNote(String claimsReturnedNote) {
		this.claimsReturnedNote = claimsReturnedNote;
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

	public String getCurrentBorrower() {
		return this.currentBorrower;
	}

	public void setCurrentBorrower(String currentBorrower) {
		this.currentBorrower = currentBorrower;
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

	public String getDescOfPieces() {
		return this.descOfPieces;
	}

	public void setDescOfPieces(String descOfPieces) {
		this.descOfPieces = descOfPieces;
	}

	public Date getDueDateTime() {
		return this.dueDateTime;
	}

	public void setDueDateTime(Date dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

	public String getEnumeration() {
		return this.enumeration;
	}

	public void setEnumeration(String enumeration) {
		this.enumeration = enumeration;
	}

	public String getFastAdd() {
		return this.fastAdd;
	}

	public void setFastAdd(String fastAdd) {
		this.fastAdd = fastAdd;
	}

	public String getFund() {
		return this.fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public int getHighDensityStorageId() {
		return this.highDensityStorageId;
	}

	public void setHighDensityStorageId(int highDensityStorageId) {
		this.highDensityStorageId = highDensityStorageId;
	}

	public String getItemDamagedNote() {
		return this.itemDamagedNote;
	}

	public void setItemDamagedNote(String itemDamagedNote) {
		this.itemDamagedNote = itemDamagedNote;
	}

	public String getItemDamagedStatus() {
		return this.itemDamagedStatus;
	}

	public void setItemDamagedStatus(String itemDamagedStatus) {
		this.itemDamagedStatus = itemDamagedStatus;
	}

	public Date getItemStatusDateUpdated() {
		return this.itemStatusDateUpdated;
	}

	public void setItemStatusDateUpdated(Date itemStatusDateUpdated) {
		this.itemStatusDateUpdated = itemStatusDateUpdated;
	}

	public int getItemStatusId() {
		return this.itemStatusId;
	}

	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}

	public int getItemTypeId() {
		return this.itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
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

	public String getMissingPieces() {
		return this.missingPieces;
	}

	public void setMissingPieces(String missingPieces) {
		this.missingPieces = missingPieces;
	}

	public int getMissingPiecesCount() {
		return this.missingPiecesCount;
	}

	public void setMissingPiecesCount(int missingPiecesCount) {
		this.missingPiecesCount = missingPiecesCount;
	}

	public Date getMissingPiecesEffectiveDate() {
		return this.missingPiecesEffectiveDate;
	}

	public void setMissingPiecesEffectiveDate(Date missingPiecesEffectiveDate) {
		this.missingPiecesEffectiveDate = missingPiecesEffectiveDate;
	}

	public String getMissingPiecesNote() {
		return this.missingPiecesNote;
	}

	public void setMissingPiecesNote(String missingPiecesNote) {
		this.missingPiecesNote = missingPiecesNote;
	}

	public int getNumOfRenew() {
		return this.numOfRenew;
	}

	public void setNumOfRenew(int numOfRenew) {
		this.numOfRenew = numOfRenew;
	}

	public String getNumPieces() {
		return this.numPieces;
	}

	public void setNumPieces(String numPieces) {
		this.numPieces = numPieces;
	}

	public Date getOrgDueDateTime() {
		return this.orgDueDateTime;
	}

	public void setOrgDueDateTime(Date orgDueDateTime) {
		this.orgDueDateTime = orgDueDateTime;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProxyBorrower() {
		return this.proxyBorrower;
	}

	public void setProxyBorrower(String proxyBorrower) {
		this.proxyBorrower = proxyBorrower;
	}

	public String getPurchaseOrderLineItemId() {
		return this.purchaseOrderLineItemId;
	}

	public void setPurchaseOrderLineItemId(String purchaseOrderLineItemId) {
		this.purchaseOrderLineItemId = purchaseOrderLineItemId;
	}

	public String getShelvingOrder() {
		return this.shelvingOrder;
	}

	public void setShelvingOrder(String shelvingOrder) {
		this.shelvingOrder = shelvingOrder;
	}

	public String getStaffOnly() {
		return this.staffOnly;
	}

	public void setStaffOnly(String staffOnly) {
		this.staffOnly = staffOnly;
	}

	public int getTempItemTypeId() {
		return this.tempItemTypeId;
	}

	public void setTempItemTypeId(int tempItemTypeId) {
		this.tempItemTypeId = tempItemTypeId;
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

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getVendorLineItemId() {
		return this.vendorLineItemId;
	}

	public void setVendorLineItemId(String vendorLineItemId) {
		this.vendorLineItemId = vendorLineItemId;
	}

	public String getVolumeNumber() {
		return this.volumeNumber;
	}

	public void setVolumeNumber(String volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

	public HoldingsRecord getHoldingsRecord() {
		return this.holdingsRecord;
	}

	public void setHoldingsRecord(HoldingsRecord holdingsRecord) {
		this.holdingsRecord = holdingsRecord;
	}

}