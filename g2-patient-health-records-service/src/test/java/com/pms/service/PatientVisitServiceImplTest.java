package com.pms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pms.exception.PatientVisitException;
import com.pms.model.PatientVisitDetails;
import com.pms.model.PrescriptionDetails;
import com.pms.model.TestDetails;
import com.pms.repositories.PatientVisitRepo;
import com.pms.repositories.PrescriptionRepo;
import com.pms.repositories.TestRepo;

@ExtendWith(MockitoExtension.class)
public class PatientVisitServiceImplTest {
	
	@InjectMocks
	PatientVisitServiceImpl service;
	@Mock
	PatientVisitRepo visitRepo;
	@Mock
	TestRepo testRepo;
	@Mock
	PrescriptionRepo precriptionRepo;
	
	PatientVisitDetails visit= new PatientVisitDetails("PHR101","1P",5.7f,70f,80,120,98.6f,72,"A+","1AP","fever",null,LocalDate.now(),null);
	TestDetails test= new TestDetails("T101","SugarTest","Positive","Highly Diabetic");
	PrescriptionDetails prescription= new PrescriptionDetails("PR101","Crocin","1-0-1","After meal");
	
	@DisplayName("JUnit test for getAllVisits method")
	@Test
	public void getAllVisits() {
		PatientVisitDetails t1 = new PatientVisitDetails();
		  PatientVisitDetails t2 = new PatientVisitDetails();
		  PatientVisitDetails t3 = new PatientVisitDetails();
		  
	      List<PatientVisitDetails> records = Arrays.asList(t1,t2,t3);
	      when(visitRepo.getAllVisitDetails("1P")).thenReturn(records);
	      
	      List<PatientVisitDetails> list=service.getAllVisit("1P");
	      assertEquals(3,list.size());	
	      verify(visitRepo,times(1)).getAllVisitDetails("1P");
	}
	
	@DisplayName("JUnit test for saveVisit method")
	@Test
	public void saveVisit() throws PatientVisitException {
		PatientVisitDetails visit= new PatientVisitDetails("PHR101","1P",5.7f,70f,80,120,98.6f,72,"A+","1AP","fever",null,LocalDate.now(),null);
		when(visitRepo.save(any(PatientVisitDetails.class))).thenReturn(visit);

        // Act
         var actual = service.saveVisit(visit);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(visit);
        verify(visitRepo, times(1)).save(visit);
        verifyNoMoreInteractions(visitRepo);
	}
	
        @DisplayName("JUnit test for saveEmployee method that throws exception")
        @Test
        public void saveVisitException() throws PatientVisitException {
        	assertThrows(PatientVisitException.class, () -> {
                service.saveVisit(visit);
            });
         
        }
        @DisplayName("JUnit test for updating visit details ")
        @Test
        public void whenUpdateVisit_thenReturnUpdatedVisit() throws PatientVisitException{
        	
        	// given - precondition or setup
        	when(visitRepo.existsById("PHR101")).thenReturn(true);
        	when(visitRepo.save(visit)).thenReturn(visit);
  
            visit.setHeight(5.8f);
            visit.setWeight(80);
            PatientVisitDetails updatedVisit = service.updateVisit("PHR101", visit);

            // then - verify the output
            assertThat(updatedVisit.getHeight()).isEqualTo(5.8f);
            assertThat(updatedVisit.getWeight()).isEqualTo(80);
        }
        
        @DisplayName("JUnit test for save visit exception method")
    	@Test
    	public void updateVisit_Exception() throws PatientVisitException {
        
        	assertThrows(PatientVisitException.class, () -> {
                service.updateVisit("PHR101", visit);
            });

        	    	}
        
        @DisplayName("JUnit test for delete one Visit method")
        @Test
        void should_delete_one_visit() throws PatientVisitException {
        	
        	when(visitRepo.existsById("PHR101")).thenReturn(true);
            doNothing().when(visitRepo).deleteById("PHR101");

            // Act & Assert
            service.deleteVisit("PHR101");
            verify(visitRepo, times(1)).deleteById("PHR101");
            verifyNoMoreInteractions(visitRepo);
        }
        
        @DisplayName("JUnit test for delete visit with Exception method")
    	@Test
    	public void delete_visit_Exception() throws PatientVisitException {
        
        	assertThrows(PatientVisitException.class, () -> {
                service.deleteVisit("PHR101");
            });
        	}
        
        @DisplayName("JUnit test to get recent Visit method")
        @Test
        void should_find_and_return_recent_visit() {
            
        	
        	when(visitRepo.findByVisitDate("PHR101")).thenReturn(visit);

            
           var actual = service.getRecentVisitDetails("PHR101");

            // Assert
            assertThat(actual).usingRecursiveComparison().isEqualTo(visit);
            verify(visitRepo, times(1)).findByVisitDate("PHR101");
            verifyNoMoreInteractions(visitRepo);
        }
   
        
 //Test Details ====================================================================================================
        
        @DisplayName("JUnit test for save test method")
    	@Test
    	public void saveTest_Exception() throws PatientVisitException {
        
        	assertThrows(PatientVisitException.class, () -> {
                service.saveTest("PHR101", test);
            });

        	    	}
        
        @DisplayName("JUnit test for get all tests method")
    	@Test
    	public void getAllTests() {
    		TestDetails t1 = new TestDetails();
    		TestDetails t2 = new TestDetails();
    		TestDetails t3 = new TestDetails();
    		  
    	      List<TestDetails> records = Arrays.asList(t1,t2,t3);
    	      when(testRepo.getAllTests("T101")).thenReturn(records);
    	      
    	      List<TestDetails> list=service.getAllTests("T101");
    	      assertEquals(3,list.size());	
    	      verify(testRepo,times(1)).getAllTests("T101");
    	}
        
        
        
        @DisplayName("JUnit test for delete one test method")
        @Test
        void should_delete_one_test() throws PatientVisitException {
        	
        	when(testRepo.existsById("T101")).thenReturn(true);
            doNothing().when(testRepo).deleteById("T101");

            // Act & Assert
            service.deleteTest("T101");
            verify(testRepo, times(1)).deleteById("T101");
            verifyNoMoreInteractions(testRepo);
        } 
        
        @DisplayName("JUnit test for delete test with Exception method")
    	@Test
    	public void delete_test_Exception() throws PatientVisitException {
        
        	assertThrows(PatientVisitException.class, () -> {
                service.deleteTest("T101");
            });
        	}
        
	//Prescription tests==============================================================================================
        
        
        @DisplayName("JUnit test for save Prescription method")
    	@Test
    	public void save_prescription_Exception() throws PatientVisitException {
        
        	assertThrows(PatientVisitException.class, () -> {
                service.savePrescription("PHR101", prescription);
            });
        	}
        
        @DisplayName("JUnit test for get all prescriptions method")
    	@Test
    	public void getAllPrescription() throws PatientVisitException {
    		PrescriptionDetails t1 = new PrescriptionDetails();
    		PrescriptionDetails t2 = new PrescriptionDetails();
    		PrescriptionDetails t3 = new PrescriptionDetails();
    		  
    	      List<PrescriptionDetails> records = Arrays.asList(t1,t2,t3);
    	      when(precriptionRepo.getPrescriptions("PHR101")).thenReturn(records);
    	      
    	      List<PrescriptionDetails> list=service.getAllPrescriptions("PHR101");
    	      assertEquals(3,list.size());	
    	      verify(precriptionRepo,times(1)).getPrescriptions("PHR101");
    	}
         
        
        @DisplayName("JUnit test for get Prescription with Exception method")
    	@Test
    	public void get_prescriptions_Exception() throws PatientVisitException {
        	//when(precriptionRepo.getPrescriptions("PHR101")).thenReturn(null);
        	assertThrows(PatientVisitException.class, () -> {
                service.getAllPrescriptions(null);
            });
        	}
        }
