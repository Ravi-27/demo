package com.mortgagedemo.app.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mortgagedemo.app.model.Mortgage;

@SpringBootTest
class MortgageServiceImplTest {

	@Autowired
	MortgageServiceImpl mortgageServiceImpl;

	@Mock
	MortgageServiceImpl mortgageServiceMockImpl;

	@Test
	void testCreateMortgageApplication() {
		mortgageServiceImpl.createMortgageApplication(getMortgageObj());
	}

	@Test
	void testCreateMortgageApplicationEmpty() {
		when(mortgageServiceMockImpl.createMortgageApplication(new Mortgage())).thenReturn(null);
	}

	@Test
	void testIsAlreadyPresentInMortgageDynamicArray() {
		mortgageServiceImpl.isAlreadyPresentInMortgageDynamicArray(getMortgageObj());
	}

	@Test
	void testIsAlreadyPresentInMortgageDynamicArrayEmpty() {
		when(mortgageServiceMockImpl.isAlreadyPresentInMortgageDynamicArray(new Mortgage())).thenReturn(false);
	}

	@Test
	void testSortByOfferDate() {
		//when(mortgageServiceMockImpl.sortByOfferDate()).thenReturn(new MortgageDynamicArray());
	}

	@Test
	void testsortByCreatedDate() {
		//when(mortgageServiceMockImpl.sortByCreatedDate()).thenReturn(new MortgageDynamicArray());
	}

	public static Mortgage getMortgageObj() {
		Mortgage obj = new Mortgage();
		obj.setOfferID("123");
		obj.setMortgageId("123");
		obj.setProductId("123");
		obj.setVersion(1);
		return obj;
	}

}

