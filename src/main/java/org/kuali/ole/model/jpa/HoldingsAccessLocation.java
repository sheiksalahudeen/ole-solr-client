package org.kuali.ole.model.jpa;

import org.kuali.ole.model.jpa.*;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * Created by sheiks 10/11/16.
 */
@Entity
@Table(name="ole_ds_access_location_t")
@NamedQuery(name="HoldingsAccessLocation.findAll", query="SELECT o FROM HoldingsAccessLocation o")
public class HoldingsAccessLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACCESS_LOCATION_ID")
	private int accessLocationId;

	@Column(name="ACCESS_LOCATION_CODE_ID")
	private int accessLocationCodeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATED")
	private Date dateUpdated;

	//bi-directional many-to-one association to HoldingsRecord
	@ManyToOne
	@JoinColumn(name="HOLDINGS_ID")
	private HoldingsRecord holdingsRecord;

	public HoldingsAccessLocation() {
	}

	public int getAccessLocationId() {
		return this.accessLocationId;
	}

	public void setAccessLocationId(int accessLocationId) {
		this.accessLocationId = accessLocationId;
	}

	public int getAccessLocationCodeId() {
		return this.accessLocationCodeId;
	}

	public void setAccessLocationCodeId(int accessLocationCodeId) {
		this.accessLocationCodeId = accessLocationCodeId;
	}

	public Date getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public HoldingsRecord getHoldingsRecord() {
		return this.holdingsRecord;
	}

	public void setHoldingsRecord(HoldingsRecord holdingsRecord) {
		this.holdingsRecord = holdingsRecord;
	}

}