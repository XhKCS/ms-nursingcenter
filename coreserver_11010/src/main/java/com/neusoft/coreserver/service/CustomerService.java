package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.Customer;

public interface CustomerService {
	int deleteCustomerById(int customerId);

	int updateCustomer(Customer updatedCustomer);

	int addCustomer(Customer customer);
}
