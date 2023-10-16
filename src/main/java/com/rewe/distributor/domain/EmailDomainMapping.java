package com.rewe.distributor.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Mapping enum for domain and partition
 * */
@Getter
public enum EmailDomainMapping {
    GOOGLE("gmail.com", 0),
    MICROSOFT("microsoft.com", 1),
    AMAZON("amazon.com", 2);

    EmailDomainMapping(String name, int partition) {
        this.name = name;
        this.partition = partition;
    }

    private final String name;
    private final int partition;

    public static EmailDomainMapping valueOfName(String domainName) {
        for (EmailDomainMapping edm : values()) {
            if (edm.name.equals(domainName)) {
                return edm;
            }
        }
        return null;
    }

    public static List<String> getAllDomainNames() {
        return Arrays.stream(EmailDomainMapping.values())
                .map(EmailDomainMapping::getName)
                .collect(Collectors.toList());
    }
}
