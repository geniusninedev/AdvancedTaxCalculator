package com.nineinfosys.android.advancedtaxcalculator;

/**
 * Created by Dev on 09-03-2017.
 */

public class AdvancedTaxMain {

    double income;
    double incomehouse;
    double incomeother;
    double profit;
    String citizenStatus;
    double deductions;
    double income1;
    double capitalgains;
    final double BASE_VALUE = 250000;
    final double BASE_VALUE_SENIOR = 300000;
    final double BASE_VALUE_SUPER_SENIOR = 500000;
    double totaltax=((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge()));
    double Assesedtax=((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())-5-2);
    private AdvanceIncomeTaxResults advanceIncomeTaxResults;


    public AdvancedTaxMain(double income, double incomehouse,double incomeother,double profit,double capitalgains,double deductions,String citizenStatus) {
        this.income = income;
        this.incomehouse = incomehouse;
        this.incomeother = incomeother;
        this.profit = profit;
        this.citizenStatus = citizenStatus;
        this.deductions=deductions;
        this.capitalgains=capitalgains;
    }
    public AdvancedTaxMain(double income1,String citizenStatus) {
        this.income1 = income1;
        this.citizenStatus = citizenStatus;
    }

    public AdvanceIncomeTaxResults getadvancedIncomeTaxResults(){
        advanceIncomeTaxResults = new AdvanceIncomeTaxResults();
        advanceIncomeTaxResults.setNettax(nettax());
        advanceIncomeTaxResults.setIncomeTaxAfterRelief(calculateIncomeTaxAfterRelief());
        advanceIncomeTaxResults.setEducationCess((calculateIncomeTaxAfterRelief())*.02);
        advanceIncomeTaxResults.setSecondaryAndHigherEducationCess((calculateIncomeTaxAfterRelief())*.01);
        advanceIncomeTaxResults.setSurcharge(calculateSurcharge());
        advanceIncomeTaxResults.setTotalTax(calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge());
        advanceIncomeTaxResults.setassessedTax(calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+(calculateSurcharge())-(5)-(2));
        advanceIncomeTaxResults.setUptoJune(uptoJune());
        advanceIncomeTaxResults.setUptoSep(uptoSep());
        advanceIncomeTaxResults.setUptodec(uptoDec());
        advanceIncomeTaxResults.setUptomarch(uptoMarch());
        advanceIncomeTaxResults.setUptomarchend(uptoMarchEnd());
        return advanceIncomeTaxResults;
    }


    public double calculateSurcharge(){
        if(income1<=5000000){
            return 0;
        }

        else if(income1>5000000&&income1<=10000000) {

            return getSurcharge();

        } else if(income1>10000000){
            return getsur();
        }else

            return calculateIncomeTaxAfterRelief();
    }

    private double getsur() {


        if(income1>10000000 && ((income1-10000000)*0.7)<(calculateIncomeTaxAfterRelief()*0.15)){

            return (income1-10000000)*0.7;

        }else {

            return (calculateIncomeTaxAfterRelief()*0.15);

        }

    }

    private double getSurcharge() {

        if(income1>5000000 && (income1-5000000)*0.7<calculateIncomeTaxAfterRelief()*0.1){

            return (income1-5000000)*0.7;

        }else {

            return (calculateIncomeTaxAfterRelief()*0.1);

        }

    }


    public double  calculateIncomeTaxAfterRelief(){

        if(citizenStatus == "Citizen"){

            double incomeTaxAfterRelief = calculateIncomeTaxCitizen();

            return incomeTaxAfterRelief;

        }

        else if(citizenStatus == "seniorCitizen"){

            double incomeTaxAfterRelief = calculateIncomeTaxSeniorCitizen();

            return incomeTaxAfterRelief;

        }

        else{

            double incomeTaxAfterRelief = calculateIncomeTaxSuperSeniorCitizen();

            return incomeTaxAfterRelief;
        }

    }
    public double uptoJune(){
        if((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())> 10000 ){
            return ((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())*15/100);
        }
        return 0;

    }
    public double uptoSep(){
        if((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())> 10000 ){
            return ((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())*45/100);
        }
        return 0;

    }public double uptoDec(){
        if((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())> 10000 ){
            return ((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())*75/100);
        }
        return 0;

    }public double uptoMarch(){
        if((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())> 10000 ){
            return ((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())*100/100);
        }

        return 0;
    }public double uptoMarchEnd(){
        if((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())> 10000 ){
            return ((calculateIncomeTaxAfterRelief()+((calculateIncomeTaxAfterRelief())*.02)+ ((calculateIncomeTaxAfterRelief())*.01)+calculateSurcharge())*100/100);
        }

        return 0;
    }


    public  double calculateIncomeTaxCitizen(){

        if (income1 <= 350000) {
            return 0;

        } else if (income1 > 300000 && income1 <= 350000) {
            return ((income1 - BASE_VALUE) * 0.05) - 2500;

        } else if (income1 > 350000 && income1 <= 500000) {
            return ((income1 - BASE_VALUE) * 0.05);

        } else if (income1 > 500000 && income1 <=1000000) {
            return ((income1 - 500000) * 0.2) + 12500;

        } else
            return ((income1 - 1000000) * 0.3) + 112500;

    }
    public  double calculateIncomeTaxSeniorCitizen(){

        if (income1 <= 350000) {
            return 0;

        } else if (income1 > 350000 && income1 <= 500000) {
            return ((income1 - BASE_VALUE_SENIOR) * 0.05);

        } else if (income1 > 500000 && income1 <=1000000) {
            return ((income1 - 500000) * 0.2) + 10000;

        } else
            return ((income1 - 1000000) * 0.3) + 110000;

    }
    public  double calculateIncomeTaxSuperSeniorCitizen(){

        if (income1 <= BASE_VALUE_SUPER_SENIOR) {
            return 0;

        } else if (income1 > BASE_VALUE_SUPER_SENIOR && income1 <=1000000) {
            return ((income1 - BASE_VALUE_SUPER_SENIOR) * 0.2) ;

        } else
            return ((income1 - 1000000) * 0.3) + 100000;


    }

    public double nettax(){
        double profits=(income+incomehouse+incomeother+capitalgains+profit-deductions);
        return profits;

    }


    public class AdvanceIncomeTaxResults{
        private double incomeTaxAfterRelief;
        private double surcharge;
        private double educationCess;
        private double secondaryAndHigherEducationCess;
        private double totalTax;
        private double assessedTax;
        private double instalments;
        private double uptojune;
        private double uptosep;
        private double uptodec;
        private double uptomarch;
        private double uptomarchend;
        private double nettax;

        public double getassessedTax() {
            return assessedTax;
        }

        public void setassessedTax(double assessedTax) {
            this.assessedTax = assessedTax;
        }
        public double getIncomeTaxAfterRelief() {
            return incomeTaxAfterRelief;
        }

        public void setIncomeTaxAfterRelief(double incomeTaxAfterRelief) {
            this.incomeTaxAfterRelief = incomeTaxAfterRelief;
        }

        public double getSurcharge() {
            return surcharge;
        }

        public void setSurcharge(double surcharge) {
            this.surcharge = surcharge;
        }

        public double getEducationCess() {
            return educationCess;
        }

        public void setEducationCess(double educationCess) {
            this.educationCess = educationCess;
        }

        public double getSecondaryAndHigherEducationCess() {
            return secondaryAndHigherEducationCess;
        }

        public void setSecondaryAndHigherEducationCess(double secondaryAndHigherEducationCess) {
            this.secondaryAndHigherEducationCess = secondaryAndHigherEducationCess;
        }

        public double getTotalTax() {
            return totalTax;

        }public void setTotalTax(double totalTax) {
            this.totalTax = totalTax;
        }
        public double getNettax() {
            return nettax;
        }

        public void setNettax(double nettax) {
            this.nettax = nettax;
        }
        public void setInstlaments(double instalments) {
            this.instalments = instalments;
        }
        public double getInstlaments() {
            return instalments;
        }
        public void setUptoJune(double uptojune) {
            this.uptojune = uptojune;
        }
        public double getUptoJune() {
            return uptojune;

        } public void setUptoSep(double uptosep) {
            this.uptosep = uptosep;
        }
        public double getUptosep() {
            return uptosep;
        }
        public void setUptodec(double uptodec) {
            this.uptodec= uptodec;
        }
        public double getUptodec() {
            return uptodec;
        } public void setUptomarch(double uptomarch) {
            this.uptomarch= uptomarch;
        }
        public double getUptomarch() {
            return uptomarch;
        } public void setUptomarchend(double Uptomarchend) {
            this.uptomarchend= Uptomarchend;
        }
        public double getUptomarchend() {
            return uptomarchend;
        }

    }
}
