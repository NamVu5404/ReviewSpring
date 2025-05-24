package com.NamVu.ReviewSpring.mapper;

import com.NamVu.ReviewSpring.dto.request.AddressRequest;
import com.NamVu.ReviewSpring.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address mapToEntity(AddressRequest request);
}
