package model;

// import model.roomType;

public class FreeRoom extends Room {
    // Double price = 0.0;

    public FreeRoom(String roomNumber, roomType enumeration) {
     super(roomNumber, 0.0, enumeration);
    }
    //constructor set price to 0
    //override toString method
    @Override

    public String toString() {
        return super.toString();}
}
