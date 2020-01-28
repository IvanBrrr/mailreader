package com.company.mailreader.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TrackerPriorityType implements EnumClass<String> {

    CURRENT("current");

    private String id;

    TrackerPriorityType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TrackerPriorityType fromId(String id) {
        for (TrackerPriorityType at : TrackerPriorityType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}