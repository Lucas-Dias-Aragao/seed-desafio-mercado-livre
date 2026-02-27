package com.dev.eficiente.desafio.marketplace.validation.validator;

import com.dev.eficiente.desafio.marketplace.validation.annotation.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    private String domainAttribute;

    private Class<?> klazz;

    private String message;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue params) {
        domainAttribute = params.fieldName();
        klazz = params.domainClass();
        message = params.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) return true;

        Query query = entityManager.createQuery("SELECT 1 FROM " + klazz.getName() + " WHERE LOWER("
                + domainAttribute + ") = LOWER(:value)");
        query.setParameter("value", value);

        return query.getResultList().isEmpty();
    }

}
