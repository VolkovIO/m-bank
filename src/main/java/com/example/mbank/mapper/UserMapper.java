package com.example.mbank.mapper;

import com.example.mbank.entity.EmailData;
import com.example.mbank.entity.PhoneData;
import com.example.mbank.entity.User;
import com.example.mbank.web.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "balance", source = "account.balance")
    @Mapping(target = "phones", source = "phones", qualifiedByName = "mapUserPhones")
    @Mapping(target = "emails", source = "emails", qualifiedByName = "mapUserEmails")
    UserResponse toDto(User user);

    @Named("mapUserPhones")
    default Set<String> mapUserPhones(Set<PhoneData> phones) {
        return phones.stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toSet());
    }

    @Named("mapUserEmails")
    default Set<String> mapUserEmails(Set<EmailData> emails) {
        return emails.stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toSet());
    }

    List<UserResponse> toDtoList(List<User> personEntities);
}
