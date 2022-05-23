package AdminDashboard;

public class Driver {
    private String name;
    private String icNumber;

    public Driver(String name, String icNumber) {
        this.name = name;
        this.icNumber = icNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }
}
