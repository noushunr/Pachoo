package com.highstreets.user.common;

import com.highstreets.user.models.Offer;

import java.util.List;

public interface OfferDetailAdapterCallback {
    void onClick(List<Offer> offerList);
}
