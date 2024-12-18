package com.example.demo.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseFilter extends SimpleFilter {

    Long id;

    String name;

    Long categoryId;
}
