package com.easybytes.Repository;


import com.easybytes.Modal.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {


	//@PreAuthorize("hasRole('ADMIN')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
