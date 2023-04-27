package model;


public interface IRoom {
    String getRoomNum();

    Double getRoomPrice();

    roomType getRoomType();

    boolean isFree();
}
