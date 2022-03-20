package turkcell.rentacar.core.adapters.concretes;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import turkcell.rentacar.core.adapters.abstracts.BankAdapterService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.externalServices.banks.GarantiBank;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
@Qualifier("garantiBank")
public class GarantiBankAdapterManager implements BankAdapterService{

	@Override
	public Result checkIfLimitIsEnough(String cardNo, String year, String mounth, String cVV, double amount) {
		
		GarantiBank garantiBank = new GarantiBank();
		if(garantiBank.isLimitExists(cardNo, year, mounth, cVV, amount))	{
			return new SuccessResult("Garanti pos işlemi yapıldı.");
		}
		else {
			throw new BusinessException("Limit yetersiz");
		}
	}

}
