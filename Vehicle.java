public  class Vehicle {
    private int vehicleNumber;
    private VehicleType type;
    public Vehicle (int vehicleNumber, VehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.type = vehicleType;
    }

    public VehicleType getType() {
        return type;
    }
}
