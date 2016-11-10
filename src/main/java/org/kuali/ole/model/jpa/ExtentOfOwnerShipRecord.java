package org.kuali.ole.model.jpa;

import org.kuali.ole.model.jpa.*;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * Created by sheiks 10/11/16.
 */
@Entity
@Table(name="ole_ds_ext_ownership_t")
@NamedQuery(name="ExtentOfOwnerShipRecord.findAll", query="SELECT o FROM ExtentOfOwnerShipRecord o")
public class ExtentOfOwnerShipRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EXT_OWNERSHIP_ID")
	private int extOwnershipId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATED")
	private Date dateUpdated;

	@Column(name="EXT_OWNERSHIP_TYPE_ID")
	private int extOwnershipTypeId;

	private int ord;

	private String text;

	//bi-directional many-to-one association to HoldingsRecord
	@ManyToOne
	@JoinColumn(name="HOLDINGS_ID")
	private HoldingsRecord holdingsRecord;

	public ExtentOfOwnerShipRecord() {
	}

	public int getExtOwnershipId() {
		return this.extOwnershipId;
	}

	public void setExtOwnershipId(int extOwnershipId) {
		this.extOwnershipId = extOwnershipId;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public int getExtOwnershipTypeId() {
		return this.extOwnershipTypeId;
	}

	public void setExtOwnershipTypeId(int extOwnershipTypeId) {
		this.extOwnershipTypeId = extOwnershipTypeId;
	}

	public int getOrd() {
		return this.ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HoldingsRecord getHoldingsRecord() {
		return this.holdingsRecord;
	}

	public void setHoldingsRecord(HoldingsRecord holdingsRecord) {
		this.holdingsRecord = holdingsRecord;
	}

}