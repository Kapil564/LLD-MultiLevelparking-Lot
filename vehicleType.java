public enum VehicleType {
    BIKE {
        @Override
        public boolean canFitInSlot(SlotType slotType) {
            return true; // fits anywhere
        }
    },
    CAR {
        @Override
        public boolean canFitInSlot(SlotType slotType) {
            return slotType == SlotType.MEDIUM || slotType == SlotType.LARGE;
        }
    },
    TRUCK {
        @Override
        public boolean canFitInSlot(SlotType slotType) {
            return slotType == SlotType.LARGE;
        }
    };

    public abstract boolean canFitInSlot(SlotType slotType);
}