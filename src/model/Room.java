package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    public final Double price;

    private final roomType enumeration;

    public Room(String roomNumber, Double price, roomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }
    @Override
    public String getRoomNum() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public roomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return price == 0;
    }
    @Override
    public String toString() {
        return
                "roomNumber:" + roomNumber +
                ", price: $" + price +
                ", type" + enumeration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        //return obj == this;
        return Objects.equals(roomNumber, room.roomNumber)
                && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }


}
