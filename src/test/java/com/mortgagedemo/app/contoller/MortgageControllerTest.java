package com.mortgagedemo.app.contoller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mortgagedemo.app.model.Mortgage;

@SpringBootTest
class MortgageControllerTest {

	@Autowired
	private MortgageController mortgageController;
	
	@Mock
	private MortgageController mortgageControllerMock;
	
	@Test
	void testInsertMortgageApplication() {
		mortgageController.insertMortgageApplication(getMortgageObj());
	}
	
	@Test
	void testInsertMortgageApplicationEmptyObj() {
		mortgageController.insertMortgageApplication(new Mortgage());
	}
	
	@Test
	void testInsertMortgageApplicationNullObj() {
		when(mortgageControllerMock.insertMortgageApplication(getMortgageObj())).thenReturn(null);
	}
	
	public static Mortgage getMortgageObj() {
		Mortgage obj = new Mortgage();
		obj.setOfferID("123");
		obj.setMortgageId("123");
		obj.setProductId("123");
		obj.setVersion(1);
		return obj;
	}

	@Test
	void testGetAllMortgageApplicationEmpty() {
		when(mortgageControllerMock.getAllMortgageApplication("")).thenReturn(null);
	}
	
	@Test
	void testGetAllMortgageApplicationNull() {
		when(mortgageControllerMock.getAllMortgageApplication(null)).thenReturn(null);
	}
	
	
}
