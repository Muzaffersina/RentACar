package turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderedAdditionalDto {
	
	private int orderedId;
	
	private int rentalId;
	
	private int additionalId;

}
