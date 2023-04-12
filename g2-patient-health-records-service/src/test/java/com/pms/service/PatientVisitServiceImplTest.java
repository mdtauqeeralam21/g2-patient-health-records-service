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
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pms.exception.PatientVisitException;
import com.pms.model.PatientVisitDetails;
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
        	PatientVisitDetails visit= new PatientVisitDetails("PHR101","1P",5.7f,70f,80,120,98.6f,72,"A+","1AP","fever",null,LocalDate.now(),null);
        	when(visitRepo.save(any(PatientVisitDetails.class))).thenReturn(null);
        	//org.junit.jupiter.api.Assertions.
        	assertThrows(PatientVisitException.class, () -> {
                service.saveVisit(visit);
            });
            //verify(visitRepo, never()).save(any(PatientVisitDetails.class));
        }
        @DisplayName("JUnit test for updating visit details ")
        @Test
        public void whenUpdateVisit_thenReturnUpdatedVisit(){
        	
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
        
        @DisplayName("JUnit test for delete one Visit method")
        @Test
        void should_delete_one_visit() {
        	
        	when(visitRepo.existsById("PHR101")).thenReturn(true);
            doNothing().when(visitRepo).deleteById("PHR101");

            // Act & Assert
            service.deleteVisit("PHR101");
            verify(visitRepo, times(1)).deleteById("PHR101");
            verifyNoMoreInteractions(visitRepo);
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
	
	
	
	}
