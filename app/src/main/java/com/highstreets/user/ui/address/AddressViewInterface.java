package com.highstreets.user.ui.address;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.address.model.Address;

import java.util.List;

public interface AddressViewInterface extends CommonViewInterface {
    void setAllAddress(List<Success> addressList);
}
