public class HourlyPrice implements PricingStrategy{
    private double pricePerHour = 110; 
    public double calculatePrice(long entryTime, long exitTime) {
        long hoursParked = (exitTime - entryTime) / (1000 * 60 * 60);
        return hoursParked * pricePerHour;
    }
}
