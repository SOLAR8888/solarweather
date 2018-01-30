package com.solarapp.solarweather.contracts;

/**
 * Created by vereskun on 02.01.2018.
 */

public class ContractDetails {

    public interface IDetailsView{
        void showMessage(String text);
        void setTitle(String name);
    }
    public interface IDetailsPresenter{
        void setSelectedCity(String id);
        void onActivityCreate();
    }

}
