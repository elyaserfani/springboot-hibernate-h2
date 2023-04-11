package com.simplespringboot.app.global;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CustomPageDto<T> {
    private List<T> items;
    private CustomMeta meta;

    public CustomPageDto(List<T> items, CustomMeta meta) {
        this.items = items;
        this.meta = meta;
    }

}