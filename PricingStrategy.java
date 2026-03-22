public interface PricingStrategy {
    double calculatePrice(long entryTime, long exitTime);
}