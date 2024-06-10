package com.kerem.mapper;

import com.kerem.dto.request.AuthRegisterRequestDto;
import com.kerem.dto.response.AuthResponseDto;
import com.kerem.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    Auth dtoToEntity(AuthRegisterRequestDto authRegisterRequestDto);

    AuthRegisterRequestDto entityToRequestDto(Auth auth);
    AuthResponseDto entityToResponseDto(Auth auth);

    List<AuthResponseDto> entityListToDtoList(List<Auth> authList);


}
