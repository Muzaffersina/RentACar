package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.User;


@Repository
public interface UserDao extends JpaRepository<User, Integer>{
	
	
	
}
