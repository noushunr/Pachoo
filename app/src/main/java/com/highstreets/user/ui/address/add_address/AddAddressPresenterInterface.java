package com.highstreets.user.ui.address.add_address;

import com.highstreets.user.common.CommonPresenterInterface;

public interface AddAddressPresenterInterface extends CommonPresenterInterface {

    void addAddress(
                    String firstName,
                    String mobile,
                    String postcode,
                    String address_1,
                    String address_2);

    void editAddress(String userId,
                     String addressId,
                     String firstName,
                     String lastName,
                     String mobile,
                     String district,
                     String city,
                     String state,
                     String postcode,
                     String address_1,
                     String address_2,
                     String latitude,
                     String longitude);

    void getAddress(String userId,
                    String addressId);


    void checkPostcode(String postcode);
}
