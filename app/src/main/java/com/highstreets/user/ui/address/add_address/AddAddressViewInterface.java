package com.highstreets.user.ui.address.add_address;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.ui.address.add_address.model.AddressSavedResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.address.model.Address;

public interface AddAddressViewInterface extends CommonViewInterface {

    void setAddress(Address address);

    void addressAddedResult(AddressSavedResponse addressSavedResponse);

}
