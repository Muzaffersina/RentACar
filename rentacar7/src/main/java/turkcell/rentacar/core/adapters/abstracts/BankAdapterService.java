package turkcell.rentacar.core.adapters.abstracts;

import turkcell.rentacar.core.utilities.results.Result;

public interface BankAdapterService {

	Result checkIfLimitIsEnough(String cardNo, String year, String mounth, String cVV, double amount);

}
