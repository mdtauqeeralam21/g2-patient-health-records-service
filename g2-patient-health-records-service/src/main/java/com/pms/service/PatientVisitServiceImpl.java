package com.pms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.PatientVisitDetails;
import com.pms.entity.PrescriptionDetails;
import com.pms.entity.TestDetails;
import com.pms.exception.PatientVisitException;
import com.pms.repositories.PatientVisitRepo;
import com.pms.repositories.PrescriptionRepo;
import com.pms.repositories.TestRepo;

@Service
public class PatientVisitServiceImpl implements PatientVisitService {
	
	@Autowired
	PatientVisitRepo patientRepo;
	
	@Autowired
	TestRepo testRepo;
	
	@Autowired
	PrescriptionRepo prescriptionRepo;
	

	@Override
	public PatientVisitDetails saveVisit(PatientVisitDetails pvd) throws PatientVisitException {

		PatientVisitDetails result = patientRepo.save(pvd);
		if(result == null) {
			throw new PatientVisitException("visit details data not correct "+result);
			//return null;
		}
		
		return result;
	}

	@Override
	public PatientVisitDetails updateVisit(String visitId,PatientVisitDetails visit) throws PatientVisitException {

		boolean present=patientRepo.existsById(visitId);
		if(present) {                              //if exist
		visit.setVisitId(visitId);
		   return patientRepo.save(visit);
		}else {
			throw new PatientVisitException("Visit Id Not found ");
		}
		
		
	}

	@Override
	public void deleteVisit(String visitId) throws PatientVisitException {
		if(patientRepo.existsById(visitId)) {
			patientRepo.deleteById(visitId);
			}else {
				throw new PatientVisitException("Visit Id Not found ");
			}

		//patientRepo.deleteById(visitId);
	}

	@Override
	public List<PatientVisitDetails> getAllVisit(String patientId) {
		
		return patientRepo.getAllVisitDetails(patientId);
	}

	
	@Override
	public PatientVisitDetails getRecentVisitDetails(String patientId) {
		return patientRepo.findByVisitDate(patientId);
	}
	
	//=================================Test Service==============================

	@Override
	public TestDetails saveTest(String visitId,TestDetails test) throws PatientVisitException {
		TestDetails status=patientRepo.findById(visitId).map(visit->{
		      test.setVisitDetails(visit);
		      return testRepo.save(test);
		    }).orElseThrow(() -> new PatientVisitException("Invalid visit ID "));
		
		return status;
		
		//return testRepo.save(tm).getTestName();
	}

	@Override
	public void deleteTest(String testId) throws PatientVisitException {
		if(testRepo.existsById(testId)) {
		testRepo.deleteById(testId);
		}else {
			throw new PatientVisitException("Id Not found ");
		}
	}

//	@Override
//	public TestDetails updateTest(String testId,TestDetails test) {
//		boolean present=testRepo.existsById(testId);
//		if(present) {                              //if exist
//		test.setTestId(testId);
//		return testRepo.save(test);
//		}else {
//			throw new ResourceNotFoundException("Id Not found ");
//		}
//		
//		
//	}

	@Override
	public List<TestDetails> getAllTests(String visitId) {
	
		return testRepo.getAllTests(visitId);
	}
	
	
	
	//===========================Prescription Service===================================

	@Override
	public PrescriptionDetails savePrescription(String visitId,PrescriptionDetails prescription) throws PatientVisitException {
		PrescriptionDetails status=patientRepo.findById(visitId).map(visit->{
		      prescription.setVisitDetails(visit);
		      return prescriptionRepo.save(prescription);
		    }).orElseThrow(() -> new PatientVisitException("Invalid visit ID"));
		
		return status;
		
		//return prescriptionRepo.save(pd).getPrescriptionName();
	}



	@Override
	public List<PrescriptionDetails> getAllPrescriptions(String visitId) throws PatientVisitException {
		if(visitId!=null) {
		return prescriptionRepo.getPrescriptions(visitId);
		}else {
			throw new PatientVisitException("Data Not found ");
		}
	}

	
	
}
