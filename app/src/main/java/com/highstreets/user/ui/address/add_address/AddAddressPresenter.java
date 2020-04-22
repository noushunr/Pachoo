package com.highstreets.user.ui.address.add_address;

public class AddAddressPresenter implements AddAddressPresenterInterface {

    private AddAddressViewInterface addAddressViewInterface;

    public AddAddressPresenter(AddAddressViewInterface addAddressViewInterface) {
        this.addAddressViewInterface = addAddressViewInterface;
    }


    @Override
    public void showProgressIndicator() {
        addAddressViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        addAddressViewInterface.dismissProgressIndicator();
    }
}
