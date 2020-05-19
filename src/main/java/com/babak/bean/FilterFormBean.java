package com.babak.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterFormBean {

    private String category;
    private List<String> manufacturer;
    private Integer minPrice;
    private Integer maxPrice;
    private String sort;
}
