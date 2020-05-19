package com.babak.bean;

import com.babak.entity.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogBean {

    private List<Product> products;
    private int numberPages;
    private int currentPage;
    private int recordsPerPage;
}
