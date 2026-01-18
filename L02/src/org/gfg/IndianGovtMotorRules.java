package org.gfg;

public interface IndianGovtMotorRules {

    void displayRC();

    void displayPUC();

    void displayInsurance();

    default String getLocation(){
        return "Not Implemented";
    }
}
