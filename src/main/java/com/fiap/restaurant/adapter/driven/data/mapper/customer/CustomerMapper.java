package com.fiap.restaurant.adapter.driven.data.mapper.customer;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import com.fiap.restaurant.core.model.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toCustomer(CustomerEntity customerEntity);

    CustomerEntity toCustomerEntity(Customer customer);

}
