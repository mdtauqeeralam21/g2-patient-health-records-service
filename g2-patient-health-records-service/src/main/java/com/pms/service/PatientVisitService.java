package com.pms.service;

import java.util.List;

import com.pms.exception.PatientVisitException;
import com.pms.model.PatientVisitDetails;
import com.pms.model.PrescriptionDetails;
import com.pms.model.TestDetails;

public interface PatientVisitService {
	public PatientVisitDetails saveVisit(PatientVisitDetails pvd) throws PatientVisitException;
	public PatientVisitDetails updateVisit(String visitId,PatientVisitDetails visit) throws PatientVisitException;
	public void deleteVisit(String patientId) throws PatientVisitException;
	public List<PatientVisitDetails> getAllVisit(String patientId);
	public PatientVisitDetails getRecentVisitDetails(String patientId);
	
	public TestDetails saveTest(String visitId,TestDetails test) throws PatientVisitException;
	public void deleteTest(String testId) throws PatientVisitException;
//	public TestDetails updateTest(String testId,TestDetails test);
	public List<TestDetails> getAllTests(String visitId);
	
	
	public PrescriptionDetails savePrescription(String visitId,PrescriptionDetails pm) throws PatientVisitException;
	public List<PrescriptionDetails> getAllPrescriptions(String visitId) throws PatientVisitException;
	
}
