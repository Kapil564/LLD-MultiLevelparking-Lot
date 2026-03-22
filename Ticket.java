
public class Ticket {
    private String ticketId;
    private long entryTime;
    private ParkingSlot slot;
    public Ticket(String ticketId, ParkingSlot slot){
        this.ticketId = ticketId;
        this.entryTime = System.currentTimeMillis();
        this.slot = slot;
    }
    public long getEntryTime() {
        return entryTime;
    }

    public ParkingSlot getSlot() {
        return slot;
    }
}
