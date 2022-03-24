package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.PaymentInfoService;
import turkcell.rentacar.business.contants.Messages;
import turkcell.rentacar.business.dtos.ListPaymentInfoDto;
import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.business.requests.delete.DeletePaymentInfoRequest;
import turkcell.rentacar.business.requests.update.UpdatePaymentInfoRequest;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.PaymentInfoDao;
import turkcell.rentacar.entities.concretes.PaymentInfo;

@Service
public class PaymentInfoManager implements PaymentInfoService{
	
	private ModelMapperService modelMapperService;
	private PaymentInfoDao paymentInfoDao;
	
	@Autowired
	public PaymentInfoManager(ModelMapperService modelMapperService, PaymentInfoDao paymentInfoDao) {
		
		this.modelMapperService = modelMapperService;
		this.paymentInfoDao = paymentInfoDao;
	}

	@Override
	public Result add(CreatePaymentInfoRequest createPaymentInfoRequest) {
		
		PaymentInfo paymentInfo = this.modelMapperService.forRequest().map(createPaymentInfoRequest, PaymentInfo.class);		
		paymentInfo.setPaymentInfoId(0);
		this.paymentInfoDao.save(paymentInfo);

		return new SuccessResult(Messages.PAYMENTINFOADD);
	}

	@Override
	public Result delete(DeletePaymentInfoRequest deletePaymentInfoRequest) {
		
		PaymentInfo paymentInfo= this.modelMapperService.forRequest().map(deletePaymentInfoRequest, PaymentInfo.class);
		this.paymentInfoDao.deleteById(paymentInfo.getPaymentInfoId());

		return new SuccessResult(Messages.PAYMENTINFODELETE);
	}

	@Override
	public Result update(UpdatePaymentInfoRequest updatePaymentInfoRequest) {
		
		PaymentInfo paymentInfo = this.modelMapperService.forRequest().map(updatePaymentInfoRequest, PaymentInfo.class);
		this.paymentInfoDao.save(paymentInfo);
		
		return new SuccessResult(Messages.PAYMENTINFOUPDATE);	
	}

	@Override
	public DataResult<List<ListPaymentInfoDto>> getAll() {
		
		var result = this.paymentInfoDao.findAll();		
		List<ListPaymentInfoDto> response = result.stream()
				.map(paymentInfo -> this.modelMapperService.forDto().map(paymentInfo, ListPaymentInfoDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListPaymentInfoDto>>(response,Messages.PAYMENTINFOLIST);
	}

	@Override
	public void save(CreatePaymentInfoRequest paymentInfo) {
		
		CreatePaymentInfoRequest createPaymentInfoRequest = new CreatePaymentInfoRequest();
		createPaymentInfoRequest.setCardNo(paymentInfo.getCardNo());
		createPaymentInfoRequest.setCVV(paymentInfo.getCVV());
		createPaymentInfoRequest.setMonth(paymentInfo.getMonth());
		createPaymentInfoRequest.setYear(paymentInfo.getYear());
		createPaymentInfoRequest.setName(paymentInfo.getName());
		
		add(createPaymentInfoRequest);		
	}
}
