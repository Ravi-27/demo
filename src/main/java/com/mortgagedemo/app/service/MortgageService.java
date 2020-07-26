package com.mortgagedemo.app.service;

import com.mortgagedemo.app.model.Mortgage;

public interface MortgageService {

	Mortgage createMortgageApplication(Mortgage mortgage);

	boolean isAlreadyPresentInMortgageDynamicArray(Mortgage mortgage);

	Mortgage[] getAllMortgageApplication(String sortBy);

}
