package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.OrderedAdditionalService;
import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorDataResult;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalDao;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {
	private ModelMapperService modelMapperService;
	private OrderedAdditionalDao orderedAdditionalDao;
	
	public OrderedAdditionalManager(ModelMapperService modelMapperService, OrderedAdditionalDao orderedAdditionalDao) {		
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalDao = orderedAdditionalDao;
	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalDto>> getAll() {
		var result = this.orderedAdditionalDao.findAll();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListOrderedAdditionalDto>>("Liste Bos");
		}
		List<ListOrderedAdditionalDto> response = result.stream()
				.map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, ListOrderedAdditionalDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListOrderedAdditionalDto>>(response);
	}
	
	
	
	
}
