package com.highstreets.user.ui.product;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.OfferDetail;
import com.highstreets.user.ui.product.model.AddToCartResponse;

public interface ShopProductsViewInterface extends CommonViewInterface {
  void setOfferDetail(OfferDetail offerDetail);

    void setAddedToCartSuccess(AddToCartResponse addToCartResponse);
}
