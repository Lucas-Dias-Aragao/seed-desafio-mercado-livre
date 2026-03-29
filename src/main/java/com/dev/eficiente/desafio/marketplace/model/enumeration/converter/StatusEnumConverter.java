package com.dev.eficiente.desafio.marketplace.model.enumeration.converter;

import com.dev.eficiente.desafio.marketplace.model.enumeration.StatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusEnumConverter implements AttributeConverter<StatusEnum, Short> {

    @Override
    public Short convertToDatabaseColumn(StatusEnum status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public StatusEnum convertToEntityAttribute(Short id) {
        if (id == null) return null;
        for (StatusEnum s : StatusEnum.values()) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Id inválido para StatusEnum: " + id);
    }
}

