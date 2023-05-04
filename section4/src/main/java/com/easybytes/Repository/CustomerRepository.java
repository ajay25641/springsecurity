package com.easybytes.Repository;

import com.easybytes.Modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    Customer findByEmail(String email);
}
