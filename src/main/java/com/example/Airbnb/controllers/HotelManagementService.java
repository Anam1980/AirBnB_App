package com.example.Airbnb.controllers;


import com.example.Airbnb.model.Booking;
import com.example.Airbnb.model.Facility;
import com.example.Airbnb.model.Hotel;
import com.example.Airbnb.model.User;

import java.util.List;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();
    public String addHotel(Hotel hotel) {
        if(hotel==null || hotel.getHotelName()==null){
            return "FAILURE";
        }
        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBooking(Integer aadharCard) {
        return hotelManagementRepository.getBooking(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}

