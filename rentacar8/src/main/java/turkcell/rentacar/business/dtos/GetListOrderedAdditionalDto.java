package turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListOrderedAdditionalDto {
	
	private int orderedId;

	private int rentalIds;

	private int additionalId;
}
