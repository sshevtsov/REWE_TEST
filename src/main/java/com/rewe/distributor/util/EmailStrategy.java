package com.rewe.distributor.util;

import com.rewe.distributor.domain.EmailDomainMapping;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Random;

/*
* Class for generating random email addresses using PODAM library
* */
public class EmailStrategy implements AttributeStrategy<String> {

    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        Random random = new Random();
        List<String> allDomainNames = EmailDomainMapping.getAllDomainNames();
        String currentDomain = allDomainNames.get(random.nextInt(allDomainNames.size()));
        return "aaa.bbb@" + currentDomain;
    }
}