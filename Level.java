import java.util.*;
public class Level {
    private int LevelId;
    private List<ParkingSlot> slots;

    private Level(int levelId, List<ParkingSlot> parkingSlots) {
        this.LevelId = levelId;
        this.slots = parkingSlots;
    }
    public ParkingSlot getAvailableSlot(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.isAvailable() && slot.canFitVehicle(vehicle)) {
                return slot;
            }
        }
        return null;
    }
}
