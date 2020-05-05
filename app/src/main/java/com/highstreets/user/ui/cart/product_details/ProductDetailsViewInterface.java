package com.highstreets.user.ui.cart.product_details;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.cart.product_details.model.ProductDetails;

public interface ProductDetailsViewInterface extends CommonViewInterface {
    void setProductDetails(ProductDetails productDetails);
}
