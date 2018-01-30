package com.solarapp.solarweather.presenters;

import com.solarapp.solarweather.contracts.ContractDetails;
import com.solarapp.solarweather.models.City;
import com.solarapp.solarweather.models.ModelMain;

/**
 * Created by vereskun on 02.01.2018.
 */

public class PresenterDetails implements ContractDetails.IDetailsPresenter {

    private ContractDetails.IDetailsView view;
    private ModelMain mainModel;
    public static City selectedCity;

    public PresenterDetails(ContractDetails.IDetailsView view, ModelMain mainModel) {
        this.view = view;
        this.mainModel = mainModel;
    }

    @Override
    public void setSelectedCity(String id) {
        selectedCity = mainModel.getCityFromRealmDBbyID(id);
    }

    @Override
    public void onActivityCreate() {

        view.setTitle(selectedCity.getName());
        //view.showMessage(selectedCity.getName());
    }
    public static String dataToString(Double data){
        String string = String.valueOf(data);
        if (string.equals("-1000.0")){
            return "-";
        }
        return string;
    }
    public static String dataToString(Integer data){
        String string = String.valueOf(data);
        if (string.equals("-1000")){
            return "-";
        }
        return string;
    }
}
