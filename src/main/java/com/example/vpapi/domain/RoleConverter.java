package com.example.vpapi.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<MemberRole, String> {

    @Override
    public String convertToDatabaseColumn(MemberRole role) {
        if (role == null) {
            return null;
        }
        return role.name();
    }

    @Override
    public MemberRole convertToEntityAttribute(String roleName) {
        if (roleName == null) {
            return null;
        }
        try {
            return MemberRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
