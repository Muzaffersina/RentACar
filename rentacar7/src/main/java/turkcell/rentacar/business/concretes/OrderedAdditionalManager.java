package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.abstracts.OrderedAdditionalService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateOrderedAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteOrderedAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateOrderedAdditionalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalDao;
import turkcell.rentacar.entities.concretes.OrderedAdditional;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {

	private ModelMapperService modelMapperService;
	private OrderedAdditionalDao orderedAdditionalDao;
	private AdditionalService additionalService;
	private RentalService rentalService;

	@Autowired
	public OrderedAdditionalManager(ModelMapperService modelMapperService, OrderedAdditionalDao orderedAdditionalDao,
			AdditionalService additionalService,RentalService rentalService) {
		
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalDao = orderedAdditionalDao;
		this.additionalService = additionalService;
		this.rentalService = rentalService ;
	}

	@Override
	public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) {
		
		this.rentalService.checkRentCarExists(createOrderedAdditionalRequest.getRentalId());
		this.additionalService.checkAdditionalExists(createOrderedAdditionalRequest.getAdditionalId());			

		OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest,
				OrderedAdditional.class);
		
		
		orderedAdditional.setOrderedId(0);
		this.orderedAdditionalDao.save(orderedAdditional);

		return new SuccessResult("OrderedAdditional. eklendi");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<ListOrderedAdditionalDto>> getAll() {

		var result = this.orderedAdditionalDao.findAll();

		List<ListOrderedAdditionalDto> response = result.stream().map(orderedAdditional -> this.modelMapperService
				.forDto().map(orderedAdditional, ListOrderedAdditionalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalDto>>(response);
	}

	@Override
	public List<ListOrderedAdditionalDto> getByAdditional_AdditionalId(int additionalId) {

		checkAdditionalId(additionalId);

		List<OrderedAdditional> result = this.orderedAdditionalDao.getByAdditional_AdditionalId(additionalId);
		List<ListOrderedAdditionalDto> response = result.stream().map(orderedAdditional -> this.modelMapperService
				.forDto().map(orderedAdditional, ListOrderedAdditionalDto.class)).collect(Collectors.toList());

		return response;
	}

	@Override
	public boolean checkAdditionalId(int additionalId) {

		var result = this.additionalService.checkAdditionalExists(additionalId);
		if (result) {
			return true;
		}
		throw new BusinessException("AdditionalId geçersiz");
	}

	@Override
	public boolean checkUsedAdditionalId(int additionalId) {
		
		var result = this.orderedAdditionalDao.getByAdditional_AdditionalId(additionalId);
		if (result==null) {
			return true;
		}
		throw new BusinessException("AdditionalId kullanımdadır silinemez..!");
	}
}
