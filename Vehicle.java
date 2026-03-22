public abstract class Vehicle {
    private int vehicleNumber;
    private VehicleType type;
    public Vehicle (int vehicleNumber, VehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.type = vehicleType;
    }
    public Vehicle getDetails() {
        return this;
    }
    public VehicleType getType() {
        return type;
    }
    public abstract boolean canFitInSlot(SlotType slotType);
}
