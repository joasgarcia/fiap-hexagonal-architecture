package com.fiap.restaurant.types.mapper.customer;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toCustomer(CustomerJpa customerJpa);

}
