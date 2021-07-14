package com.highstreets.user.ui.product;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.CartResponse;
import com.highstreets.user.models.OfferDetail;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.product.model.AddToCartResponse;

import java.util.List;

public interface ShopProductsViewInterface extends CommonViewInterface {
    void setProductList(List<Success> productList);

    void setAddedToCartSuccess(ProductResult addToCartResponse);

    void cityChanged(AddToCartResponse addToCartResponse);

    void cartCleared(AddToCartResponse body);
}
