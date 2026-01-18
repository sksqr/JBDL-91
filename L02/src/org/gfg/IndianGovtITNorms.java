package org.gfg;

public interface IndianGovtITNorms extends IndianEnvNorm, IndianElectronicNorms, InternationalITNorms {

    public static String getCountryCode(){
        return code;
    }

//final static String code ="IND";
String code ="IND";

    void specificToIndia();
}
