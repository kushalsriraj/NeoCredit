package rutherfordit.com.instasalary.models;

public class LoanListModel {



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getApplication_number() {
        return application_number;
    }

    public void setApplication_number(String application_number) {
        this.application_number = application_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public String getMaker_status() {
        return maker_status;
    }

    public void setMaker_status(String maker_status) {
        this.maker_status = maker_status;
    }

    public String getAuthoriser_status() {
        return authoriser_status;
    }

    public void setAuthoriser_status(String authoriser_status) {
        this.authoriser_status = authoriser_status;
    }

    public String getIntrest() {
        return intrest;
    }

    public void setIntrest(String intrest) {
        this.intrest = intrest;
    }

    public String getTransacation_enter_date() {
        return transacation_enter_date;
    }

    public void setTransacation_enter_date(String transacation_enter_date) {
        this.transacation_enter_date = transacation_enter_date;
    }

    public String getJoined_on() {
        return joined_on;
    }

    public void setJoined_on(String joined_on) {
        this.joined_on = joined_on;
    }

    public String getJoined_on_human() {
        return joined_on_human;
    }

    public void setJoined_on_human(String joined_on_human) {
        this.joined_on_human = joined_on_human;
    }

    String application_number;
    String amount;
    String description;
    String application_status;
    String maker_status;
    String authoriser_status;
    String intrest;
    String transacation_enter_date;
    String joined_on;
    String joined_on_human;
    String id;
    String user_id;
    String company_id;

    public String getEligible_amount() {
        return eligible_amount;
    }

    public void setEligible_amount(String eligible_amount) {
        this.eligible_amount = eligible_amount;
    }

    String eligible_amount;
}
