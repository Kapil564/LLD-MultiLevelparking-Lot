import java.util.*;
public class ParkingManager {
    List<Level> levels;
    private Map<String, Ticket> activeTickets;
    private PricingStrategy pricingStrategy;
    public ParkingManager(List<Level> levels, PricingStrategy pricingStrategy) {
        this.levels = levels;
        this.pricingStrategy = pricingStrategy;
        this.activeTickets = new HashMap<>();
    }
    public Ticket parkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            ParkingSlot slot = level.getAvailableSlot(vehicle);
            if (slot != null) {
                slot.parkVehicle(vehicle);

                String ticketId = UUID.randomUUID().toString();
                Ticket ticket = new Ticket(ticketId, slot);

                activeTickets.put(ticketId, ticket);
                return ticket;
            }
        }
        throw new RuntimeException("Parking Full");
    }
    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new RuntimeException("Invalid Ticket");
        }

        long exitTime = System.currentTimeMillis();
        double fee = pricingStrategy.calculatePrice(ticket.getEntryTime(), exitTime);

        ticket.getSlot().removeVehicle();
        activeTickets.remove(ticketId);

        return fee;
    }
}
