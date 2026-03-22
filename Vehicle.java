public abstract class Vehicle {
    private int vehicleNumber;
    private vehicleType type;
    public Vehicle (int vehicleNumber, vehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.type = vehicleType;
    }
    public Vehicle getDetails() {
        return this;
    }
    public vehicleType getType() {
        return type;
    }
}
