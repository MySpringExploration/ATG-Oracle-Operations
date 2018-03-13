package org.iqra.operationsapp.repo;


import org.iqra.operationsapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
}
