package turkcell.rentacar.core.adapters.concretes;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import turkcell.rentacar.core.adapters.abstracts.BankAdapterService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.externalServices.banks.IsBank;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
@Primary
public class IsBankAdapterManager implements BankAdapterService{

	@Override
	public Result checkIfLimitIsEnough(String cardNo, String year, String mounth, String cVV, double amount) {
		IsBank isBank= new IsBank();
		if(isBank.isLimitExists(cardNo, year, mounth, cVV, amount))	{
			return new SuccessResult("İsBank pos işlemi yapıldı.");
		}
		else {
			throw new BusinessException("Limit yetersiz");
		}
	}

}
