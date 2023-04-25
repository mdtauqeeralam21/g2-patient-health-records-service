package com.pms.entity;

import java.time.LocalDate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class LastUpdateListener {
	@PreUpdate
	public void setLastModifiedDate(PatientVisitDetails p) {
		p.setLastModifiedDate(LocalDate.now());
	}

	@PrePersist
	public void setCreatedDateGd(PatientVisitDetails p) {
		p.setCreatedDate(LocalDate.now());
	}
}