package com.mortgagedemo.app.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mortgagedemo.app.exceptionhandler.APIException;
import com.mortgagedemo.app.model.Mortgage;
import com.mortgagedemo.app.repository.MortgageDynamicArray;
import com.mortgagedemo.app.utility.DateUtils;

@Service
public class MortgageServiceImpl implements MortgageService {

	@Autowired
	private MortgageDynamicArray mortgageDynamicArray;

	@Override
	public Mortgage createMortgageApplication(Mortgage mortgage) {
		validateObject(mortgage);
		if (!isAlreadyPresentInMortgageDynamicArray(mortgage)) {
			if (mortgage.getOfferDate() != null && DateUtils.isOfferDateIsBeforeSixMonths(mortgage.getOfferDate())) {
				int key = checkMortgageApplicationsVirsion(mortgage);

				switch (key) {
				case -2:
					throw new APIException(
							"Mortgage Applications received by the bank is rejected because of lower version",
							HttpStatus.CONFLICT);
				case -1:
					return mortgageDynamicArray.add(mortgage);
				default:
					return mortgageDynamicArray.set(key, mortgage);
				}
			} else {
				throw new APIException("Mortgage Applications Offer Date is less than Todays date + 6 months",
						HttpStatus.CONFLICT);
			}
		} else {
			// return new ResponseEntity<Mortgage>(mortgage, HttpStatus.CONFLICT);
			throw new APIException("Mortgage Applications Already Present", HttpStatus.CONFLICT);
		}

		// return mortgageDynamicArray.add(mortgage);
	}

	public void validateObject(Mortgage mortgage) {
		if (mortgage == null) {
			throw new APIException("Mortgage Applications Object must not be null", HttpStatus.NOT_FOUND);
		} else if (mortgage.getMortgageId() == null || mortgage.getMortgageId().equals("")) {
			throw new APIException("Mortgage Applications MortgageId must not be null", HttpStatus.PARTIAL_CONTENT);
		} else if (mortgage.getVersion() < 1) {
			throw new APIException("Mortgage Applications Version must be positive integers and greater than 0",
					HttpStatus.PARTIAL_CONTENT);
		} else if (mortgage.getOfferID() == null || mortgage.getOfferID().equals("")) {
			throw new APIException("Mortgage Applications OfferID must not be null", HttpStatus.PARTIAL_CONTENT);
		} else if (mortgage.getProductId() == null || mortgage.getProductId().equals("")) {
			throw new APIException("Mortgage Applications ProductId must not be null", HttpStatus.PARTIAL_CONTENT);
		} else if (mortgage.getOfferDate() == null || mortgage.getOfferDate().equals("")) {
			throw new APIException("Mortgage Applications OfferDate must not be null", HttpStatus.PARTIAL_CONTENT);
		} else if (mortgage.getOfferExpired() == null || mortgage.getOfferExpired().equals(' ')) {
			throw new APIException("Mortgage Applications OfferExpired must not be null", HttpStatus.PARTIAL_CONTENT);
		}
		// Objects.requireNonNull(mortgage);
	}

	public int checkMortgageApplicationsVirsion(Mortgage mortgage) {
		int key = -1;
		// sortByVersion();
		if (mortgage.getMortgageId() != null && mortgage.getOfferID() != null) {
			for (int i = mortgageDynamicArray.array.length - 1; i >= 0; i--) {
				if (mortgageDynamicArray.array[i] != null && mortgageDynamicArray.array[i].getProductId() != null
						&& mortgageDynamicArray.array[i].getOfferID() != null
						&& mortgageDynamicArray.array[i].getProductId().equals(mortgage.getProductId())
						&& mortgageDynamicArray.array[i].getOfferID().equals(mortgage.getOfferID())) {
					if (mortgage.getVersion() < mortgageDynamicArray.array[i].getVersion())
						return -2;
					else if (mortgage.getVersion() == mortgageDynamicArray.array[i].getVersion())
						return i;
					else
						return -1;
				}
			}
		}

		return key;
	}

	public boolean isMortgageApplicationsVirsionIsLower(Mortgage mortgage) {
		boolean isMortgageApplicationsVirsionIsLower = false;

		if (mortgage.getMortgageId() != null && mortgage.getOfferID() != null) {
			for (int i = 0; i < mortgageDynamicArray.size; i++) {
				if (mortgageDynamicArray.array[i].getProductId() != null
						&& mortgageDynamicArray.array[i].getOfferID() != null
						&& mortgageDynamicArray.array[i].getProductId().equals(mortgage.getProductId())
						&& mortgageDynamicArray.array[i].getOfferID().equals(mortgage.getOfferID())
						&& mortgage.getVersion() < mortgageDynamicArray.array[i].getVersion()) {
					return true;
				}
			}
		}

		return isMortgageApplicationsVirsionIsLower;
	}

	public MortgageDynamicArray sortByVersion(Mortgage mortgage) {
		MortgageDynamicArray sortedDynamicArray = new MortgageDynamicArray();
		MortgageDynamicArray sortedDynamicArrayTemp = new MortgageDynamicArray();
		Mortgage tempMortgage;
		for (int i = 0; i < mortgageDynamicArray.size; i++) {
			if (mortgageDynamicArray.array[i] != null
					&& mortgageDynamicArray.array[i].getOfferID().equals(mortgage.getOfferID())
					&& mortgageDynamicArray.array[i].getProductId().equals(mortgage.getProductId()))
				sortedDynamicArrayTemp.add(mortgageDynamicArray.array[i]);
		}
		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			for (int j = i + 1; j < sortedDynamicArrayTemp.size; j++) {
				if (sortedDynamicArrayTemp.array[i] != null && sortedDynamicArrayTemp.array[j] != null
						&& sortedDynamicArrayTemp.array[i].getOfferID().equals(mortgage.getOfferID())
						&& sortedDynamicArrayTemp.array[j].getProductId().equals(mortgage.getProductId())
						&& (sortedDynamicArrayTemp.array[i].getVersion() < sortedDynamicArrayTemp.array[j]
								.getVersion())) {
					tempMortgage = sortedDynamicArrayTemp.array[i];
					sortedDynamicArrayTemp.array[i] = sortedDynamicArrayTemp.array[j];
					sortedDynamicArrayTemp.array[j] = tempMortgage;

				}
			}
		}
		System.out.println("Array elements in descending order by Version:");

		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			System.out.println("sortedDynamicArrayTemp.array[" + i + "]" + sortedDynamicArrayTemp.array[i] + " ");
			if (sortedDynamicArrayTemp.array[i] != null) {
				System.out.println(i);
				sortedDynamicArray.add(sortedDynamicArrayTemp.array[i]);
				// sortedDynamicArray.add(mortgageDynamicArray.array[i]);
			}
		}
		return sortedDynamicArray;
	}

	public Mortgage[] sortByOfferDate() throws CloneNotSupportedException {
		MortgageDynamicArray sortedDynamicArray = new MortgageDynamicArray();
		MortgageDynamicArray sortedDynamicArrayTemp = mortgageDynamicArray.clone();
		Mortgage tempMortgage;
		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			for (int j = i + 1; j < sortedDynamicArrayTemp.size; j++) {
				if (sortedDynamicArrayTemp.array[i] != null && sortedDynamicArrayTemp.array[j] != null
						&& (DateUtils.convertDateForComparison(sortedDynamicArrayTemp.array[i].getOfferDate()).before(
								DateUtils.convertDateForComparison(sortedDynamicArrayTemp.array[j].getOfferDate())))) {
					tempMortgage = sortedDynamicArrayTemp.array[i];
					sortedDynamicArrayTemp.array[i] = sortedDynamicArrayTemp.array[j];
					sortedDynamicArrayTemp.array[j] = tempMortgage;
				}
			}
		}
		System.out.println("Array elements in descending order by Offer Date:");
		/*
		 * for (int i = 0; i < mortgageDynamicArray.size; i++) {
		 * System.out.println("mortgageDynamicArray.array[" + i + "]" +
		 * mortgageDynamicArray.array[i] + " "); if (mortgageDynamicArray.array[i] !=
		 * null) sortedDynamicArray.add(mortgageDynamicArray.array[i]); }
		 */
		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			System.out.println("sortedDynamicArrayTemp.array[" + i + "]" + sortedDynamicArrayTemp.array[i] + " ");
			if (sortedDynamicArrayTemp.array[i] != null) {
				System.out.println(i);
				sortedDynamicArray.add(sortedDynamicArrayTemp.array[i]);
			} else {
				i++;
				System.out.println(i);
//count--;
			}
		}
		return sortedDynamicArray.array;
	}

	public Mortgage[] sortByCreatedDate() throws CloneNotSupportedException {
		MortgageDynamicArray sortedDynamicArray = new MortgageDynamicArray();
		MortgageDynamicArray sortedDynamicArrayTemp = mortgageDynamicArray.clone();
		Mortgage tempMortgage;
		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			for (int j = i + 1; j < sortedDynamicArrayTemp.size; j++) {
				if (sortedDynamicArrayTemp.array[i] != null && sortedDynamicArrayTemp.array[j] != null
						&& (DateUtils.convertDateForComparison(sortedDynamicArrayTemp.array[i].getCreatedDate())
								.before(DateUtils
										.convertDateForComparison(sortedDynamicArrayTemp.array[j].getCreatedDate())))) {
					tempMortgage = sortedDynamicArrayTemp.array[i];
					sortedDynamicArrayTemp.array[i] = sortedDynamicArrayTemp.array[j];
					sortedDynamicArrayTemp.array[j] = tempMortgage;
				}
			}
		}
		System.out.println("Array elements in descending order by Created Date:");
		/*
		 * for (int i = 0; i < mortgageDynamicArray.size; i++) {
		 * System.out.println("mortgageDynamicArray.array[" + i + "]" +
		 * mortgageDynamicArray.array[i] + " "); if (mortgageDynamicArray.array[i] !=
		 * null) sortedDynamicArray.add(mortgageDynamicArray.array[i]); }
		 */
		for (int i = 0; i < sortedDynamicArrayTemp.size; i++) {
			System.out.println("sortedDynamicArrayTemp.array[" + i + "]" + sortedDynamicArrayTemp.array[i] + " ");
			if (sortedDynamicArrayTemp.array[i] != null) {
				System.out.println(i);
				sortedDynamicArray.add(sortedDynamicArrayTemp.array[i]);
			} else {
				i++;
				System.out.println(i);
//count--;
			}
		}
		return sortedDynamicArray.array;
	}

	@Override
	public boolean isAlreadyPresentInMortgageDynamicArray(Mortgage mortgage) {
		return mortgageDynamicArray.isAlreadyPresentInMortgageDynamicArray(mortgageDynamicArray, mortgage);
	}

	@Override
	public Mortgage[] getAllMortgageApplication(String sortBy) {
		Mortgage[] mortgages = null;
		try {
			mortgages = (sortBy != null && sortBy.equalsIgnoreCase("sortByOfferDate")) ? sortByOfferDate()
					: sortByCreatedDate();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return mortgages;
	}

	@Scheduled(fixedRate = 5000)
	public void autoUpdateflge() {
		System.out.println("fixedRate = 5000");
		String createdDate = DateUtils.getTodaysDate();

		Mortgage mortgage1 = new Mortgage("M1", 1, "OI-1", "B1", "20/05/2020", createdDate, 'N');
		Mortgage mortgage2 = new Mortgage("M2", 2, "OI-2", "B1", "20/05/2021", createdDate, 'N');
		Mortgage mortgage3 = new Mortgage("M3", 1, "OI-1", "B1", "20/05/2021", createdDate, 'N');
		Mortgage mortgage4 = new Mortgage("M4", 3, "OI-3", "B2", "20/05/2014", createdDate, 'Y');

		mortgageDynamicArray.add(mortgage1);
		mortgageDynamicArray.add(mortgage2);
		mortgageDynamicArray.add(mortgage3);
		mortgageDynamicArray.add(mortgage4);
	}

	// @Scheduled(cron = "0 0 * * * *")
	@Scheduled(cron = "0/15 * * * * *")
	public void automaticallyUpdateExpireFlag() {
		System.out.println("cron = 0/15 * * * *");
		for (int i = 0; i < mortgageDynamicArray.size; i++) {
			if (mortgageDynamicArray.array[i] != null) {
				Date createdDate = DateUtils.convertDateForComparison(DateUtils.getTodaysDate());
				Date offerDate = DateUtils.convertDateForComparison(mortgageDynamicArray.array[i].getOfferDate());
				System.out.println("getCreatedDate[" + i + "]" + mortgageDynamicArray.array[i].getCreatedDate());
				System.out.println("getOfferDate[" + i + "]" + mortgageDynamicArray.array[i].getOfferDate());
				if (createdDate.after(offerDate)) {
					System.out.println(i);
					Mortgage mortgage = mortgageDynamicArray.array[i];
					mortgage.setOfferExpired('Y');
					mortgageDynamicArray.set(i, mortgage);
					System.out.println("mortgageDynamicArray[" + i + "]" + mortgageDynamicArray.array[i]);
				}
			}
		}
	}

}
