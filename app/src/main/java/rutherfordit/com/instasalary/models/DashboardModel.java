package rutherfordit.com.instasalary.models;

public class DashboardModel {

    String application_number, amount, joined_on, application_status;

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

    public String getJoined_on() {
        return joined_on;
    }

    public void setJoined_on(String joined_on) {
        this.joined_on = joined_on;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }
}
