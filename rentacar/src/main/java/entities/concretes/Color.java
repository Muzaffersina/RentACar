package entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colors")
@Entity
public class Color {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // otomatik artmasi.
	@Column(name = "color_id")
	private int id;
	
	@Column(name = "color_name")
	private String name;

}
