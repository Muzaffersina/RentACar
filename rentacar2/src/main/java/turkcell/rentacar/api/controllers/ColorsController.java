package turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.ColorService;
import turkcell.rentacar.business.dtos.ListColorDto;
import turkcell.rentacar.business.requests.create.CreateColorRequest;
import turkcell.rentacar.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListColorDto>>  getAll(){
		return colorService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException{
		return this.colorService.add(createColorRequest);
	}
	
	@GetMapping("/getcolorid")
	public DataResult<ListColorDto>  getByColorId(int colorId) throws BusinessException{
		return this.colorService.getByColorId(colorId);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest ) throws BusinessException{
		return this.colorService.delete(deleteColorRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequst) throws BusinessException{
		return this.colorService.update(updateColorRequst);
	}

	

}
