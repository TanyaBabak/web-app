package com.babak.utils;

import com.babak.bean.FilterFormBean;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.constants.UtilConstants;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparatorFilterFormBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorFilterFormBean.class);

    /**
     * Compare input fields input object and static.
     *
     * @param filter the object FilterFormBean
     */
    public boolean compareFilter(FilterFormBean filter) {
        LOGGER.debug("Fields input object are compare");
        boolean comparatorPrice = checkPrice(filter.getMinPrice(), filter.getMaxPrice());
        boolean comparatorString = checkString(filter.getManufacturer(), filter.getCategory(), filter.getSort());

        return comparatorPrice && comparatorString;

    }

    private boolean checkPrice(int minPrice, int maxPrice) {
        return UtilConstants.MIN_PRICE == minPrice && UtilConstants.MAX_PRICE == maxPrice;
    }

    private boolean equalsString(String category, String sort) {
        return "0".equals(category) && JspConstants.PRODUCT_NAME_ASC.equals(sort);
    }

    private boolean checkString(List<String> manufacture, String category, String sort) {
        return equalsString(category, sort) && Objects.isNull(manufacture);
    }
}
