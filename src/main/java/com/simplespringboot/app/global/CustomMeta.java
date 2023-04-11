package com.simplespringboot.app.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomMeta {
    private int page;
    private int pageSize;
    private long total;
}