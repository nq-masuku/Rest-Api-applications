package com.library.LibraryApplication.repository;

import com.library.LibraryApplication.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByFirstNameIgnoreCase(String name);
    Optional<Customer> findByEmailIgnoreCase(String email);

    @Query(value = "SELECT c FROM Customer c WHERE c.account.amountDueToCustomer > 0.00")
    List<Customer> findByAmountOwing();
}
