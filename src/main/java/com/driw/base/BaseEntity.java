package com.driw.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    protected void setId(Long id) {
    }
}
