# Multi-Level Parking Lot System

A Java implementation of a multi-level parking lot that supports different vehicle types and slot sizes, with pluggable pricing strategies.

---

## Project Structure

```
ParkingLot/
├── Vehicle.java
├── VehicleType.java
├── SlotType.java
├── ParkingSlot.java
├── Level.java
├── Ticket.java
├── PricingStrategy.java
├── HourlyPrice.java
└── ParkingManager.java
```

---

## How It Works

When a vehicle arrives, `ParkingManager` scans through each `Level` and asks it for an available `ParkingSlot` that can fit the vehicle. If one is found, the vehicle is parked and a `Ticket` is issued. When the vehicle leaves, the ticket is used to calculate the fee via the configured `PricingStrategy`, and the slot is freed.

---

## Class Descriptions

### `Vehicle`
Represents a vehicle entering the parking lot. Holds a vehicle number and a `VehicleType`. It is a plain data class — it has no parking logic of its own.

```java
Vehicle car = new Vehicle(1234, VehicleType.CAR);
```

---

### `VehicleType` _(enum)_
An enum that defines the three supported vehicle categories: `BIKE`, `CAR`, and `TRUCK`. Each constant implements the abstract method `canFitInSlot(SlotType)`, which encodes the slot compatibility rules for that vehicle type:

| Vehicle | Fits in SMALL | Fits in MEDIUM | Fits in LARGE |
|---------|:---:|:---:|:---:|
| BIKE    | ✅  | ✅  | ✅  |
| CAR     | ❌  | ✅  | ✅  |
| TRUCK   | ❌  | ❌  | ✅  |

This is where the Open/Closed Principle applies — adding a new vehicle type (e.g. `VAN`) only requires a new enum constant here. No other class needs to change.

---

### `SlotType` _(enum)_
Defines the three physical sizes a parking slot can be: `SMALL`, `MEDIUM`, and `LARGE`. Used by `VehicleType.canFitInSlot()` and stored inside each `ParkingSlot`.

---

### `ParkingSlot`
Represents a single physical parking space. It knows its own ID, size (`SlotType`), and whether it is currently occupied. It exposes methods to park and remove a vehicle, and delegates the slot-fit decision to the vehicle's type:

```java
public boolean canFitVehicle(Vehicle vehicle) {
    return vehicle.getType().canFitInSlot(this.slotType);
}
```

This keeps `ParkingSlot` stable — it never needs to change when new vehicle types are added.

---

### `Level`
Represents one floor of the parking structure. Each level holds a list of `ParkingSlot` objects. Its only job is to find and return the first available slot that can fit a given vehicle:

```java
public ParkingSlot getAvailableSlot(Vehicle vehicle) { ... }
```

Returns `null` if no suitable slot exists on that floor.

---

### `Ticket`
Issued when a vehicle is successfully parked. Records the `ticketId`, the `entryTime` (captured at creation using `System.currentTimeMillis()`), and a reference to the occupied `ParkingSlot`. Used later during exit to calculate the parking fee and free the slot.

---

### `PricingStrategy` _(interface)_
Defines the contract for any fee calculation logic:

```java
public interface PricingStrategy {
    double calculatePrice(long entryTime, long exitTime);
}
```

`ParkingManager` depends on this interface, not on any concrete implementation. This is the Dependency Inversion Principle in action — you can swap pricing logic (hourly, flat-rate, peak-hour, etc.) without touching any other class.

---

### `HourlyPrice`
A concrete implementation of `PricingStrategy`. Calculates the fee by dividing the total duration by milliseconds-per-hour and multiplying by a fixed rate (₹110/hour by default).

```java
long hoursParked = (exitTime - entryTime) / (1000 * 60 * 60);
return hoursParked * pricePerHour;
```

To add a different pricing model (flat rate, weekend surcharge, etc.), create a new class that implements `PricingStrategy` and pass it into `ParkingManager`.

---

### `ParkingManager`
The central coordinator of the system. It holds all `Level` objects and the active `Ticket` map. Exposes two primary operations:

- **`parkVehicle(Vehicle)`** — iterates through levels, finds the first available fitting slot, parks the vehicle, creates a ticket, stores it, and returns it. Throws a `RuntimeException` if the lot is full.
- **`unparkVehicle(String ticketId)`** — looks up the ticket, calculates the fee using the injected `PricingStrategy`, frees the slot, removes the ticket from the active map, and returns the fee.

---

## SOLID Principles Applied

| Principle | How |
|---|---|
| **Single Responsibility** | Each class has one clear job — `ParkingSlot` manages a space, `Ticket` records entry, `Level` finds slots |
| **Open/Closed** | New vehicle types are added as enum constants in `VehicleType` — zero changes elsewhere |
| **Liskov Substitution** | No inheritance hierarchy means no substitution issues |
| **Interface Segregation** | `PricingStrategy` is a single-method interface — lean and focused |
| **Dependency Inversion** | `ParkingManager` depends on the `PricingStrategy` interface, not `HourlyPrice` directly |

---

## Example Usage

```java
// Setup
List<ParkingSlot> slots = List.of(
    new ParkingSlot(1, SlotType.SMALL),
    new ParkingSlot(2, SlotType.MEDIUM),
    new ParkingSlot(3, SlotType.LARGE)
);
List<Level> levels = List.of(new Level(1, slots));
ParkingManager manager = new ParkingManager(levels, new HourlyPrice());

// Park a vehicle
Vehicle car = new Vehicle(1234, VehicleType.CAR);
Ticket ticket = manager.parkVehicle(car);
System.out.println("Ticket ID: " + ticket.getTicketId());

// Unpark and get fee
double fee = manager.unparkVehicle(ticket.getTicketId());
System.out.println("Fee: ₹" + fee);
```

---

## Extending the System

**Add a new vehicle type** — add a constant to `VehicleType` with its `canFitInSlot` logic. Nothing else changes.

**Add a new pricing model** — create a class implementing `PricingStrategy` and pass it to `ParkingManager`. Nothing else changes.

**Add more levels/slots** — create additional `Level` and `ParkingSlot` instances and pass them into `ParkingManager`. Nothing else changes.
