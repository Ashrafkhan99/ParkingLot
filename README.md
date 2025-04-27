# ParkingLot

## Assumptions
- Drivers always provide the correct vehicle type at entry.
- Lost ticket scenarios are not handled.
- Concurrency and race conditions are not considered.
- Vehicles exit from the same floor they entered.
- Parking charges are calculated based on vehicle type and duration.
- Each floor has exactly one entry and one exit point.
- Tickets uniquely identify a vehicle’s parking session.

---

## Overview and Core Concepts

### Parking Lot Structure
- The parking lot consists of multiple floors.
- Each floor has exactly one entry point and one exit point.
- Each floor contains multiple parking spaces (slots) designated for different vehicle types.

### Supported Vehicle Types
- Bike
- Car
- Lorry

### Entry and Exit Process

#### Entry (Check-in)
- Vehicles can enter through the entry point of any floor.
- The driver provides the vehicle type upon entry.
- **Assumption:** The driver always provides the correct vehicle type.

#### Exit (Check-out)
- Vehicles must exit through the same floor they entered.
- The exit point validates the parking ticket and calculates the parking fee.

### Ticket Details
- A ticket is generated upon successful vehicle entry.
- Ticket contains:
  - Unique ticket ID
  - Vehicle type
  - Assigned floor number
  - Assigned parking space number
  - Entry timestamp
  - Parking charge rate (based on vehicle type)
  - Amount to be paid (calculated at exit)
- The ticket is linked to a specific vehicle and used to locate the assigned parking space.
- **Assumption:** Lost ticket scenarios are not handled.

### Concurrency
- Concurrency and simultaneous access handling are not considered in this design.

---

## System Workflow

1. **Vehicle Arrival**  
   A vehicle arrives at any floor’s entry point.  
   The driver provides the vehicle type.

2. **Parking Space Search**  
   The entry point queries the parking lot system.  
   The system selects the "nearest" floor with available space for that vehicle type.  
   The floor system checks for an available parking space matching the vehicle size/type.

3. **Ticket Generation or Entry Denial**  
   - **Success:**  
     - A ticket is generated.  
     - The parking space is marked as occupied.  
     - The ticket is handed to the driver.  
   - **Failure:**  
     - No ticket is generated.  
     - The vehicle is denied entry.

4. **Parking**  
   The driver uses the ticket to locate and park in the assigned space.

5. **Vehicle Exit**  
   The driver presents the ticket at the exit point of the same floor.  
   The exit point validates the ticket.  
   The parking fee is calculated based on duration and vehicle type.  
   The parking space is marked as unoccupied.  
   The vehicle exits the parking lot.

---

## Parking Space Allocation Strategy

### Nearest Floor Allocation
- The system allocates parking spaces based on the "nearest" floor concept.
- **Definition of Nearest:** The floor with the highest number of available parking spaces for the given vehicle type.
- This approach prioritizes floors with more vacancies to reduce search time for drivers.

### Tie-Breaking Rule
- If multiple floors have the same number of available spaces for a vehicle type, the floor with the lowest floor number is selected.

### Full Capacity Handling
- If no floors have available spaces for the vehicle type, no ticket is issued, and entry is denied.

### Occupancy Updates
- When a vehicle checks in, the assigned parking space’s `occupied` status is set to `true`.
- When a vehicle checks out, the parking space’s `occupied` status is set to `false`.

---

## Exception Handling

### Custom Exceptions
The system defines specific exceptions to handle error scenarios:

- `NoSpaceAvailableException` - No available parking space for the vehicle type.
- `InvalidVehicleTypeException` - Provided vehicle type is invalid.
- `InvalidFloorNumberException` - Floor number is invalid.
- `InvalidParkingSpaceException` - Parking space is invalid.
- `InvalidEntryPointException` - Entry point is invalid.
- `InvalidExitPointException` - Exit point is invalid.
- `InvalidParkingLotException` - Parking lot is invalid.
- `InvalidTicketException` - Ticket is invalid.
