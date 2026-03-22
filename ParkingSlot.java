
public class ParkingSlot {
    private int slotId;
    private SlotType slotType;
    private Vehicle vehicle;
    private boolean isOccupied;
    public ParkingSlot(int slotId, SlotType slotType){
        this.slotId = slotId;
        this.slotType = slotType;
        this.isOccupied = false;
        this.vehicle = null;
    }
    public boolean isAvailable(){
        return !isOccupied;
    }
    public void parkVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isOccupied = true;
    }
    public void removeVehicle(){
        this.vehicle = null;
        this.isOccupied = false;
    }  

    public boolean canFitVehicle(Vehicle vehicle) {
        return switch (vehicle.getType()) {
            case BIKE -> true; // can fit anywhere
            case CAR -> this.slotType == SlotType.MEDIUM || this.slotType == SlotType.LARGE;
            case TRUCK -> this.slotType == SlotType.LARGE;
        };
    }
}