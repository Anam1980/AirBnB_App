package com.example.Airbnb.controllers;

import com.example.Airbnb.model.Booking;
import com.example.Airbnb.model.Facility;
import com.example.Airbnb.model.Hotel;
import com.example.Airbnb.model.User;

import java.util.*;

public class HotelManagementRepository {
    Map<Integer, User> userdb = new HashMap<>();
    Map<String, Hotel> hoteldb = new HashMap<>();
    Map<String, Booking> bookingdb = new HashMap<>();

    Map<Integer, List<String>>PersonBookingdb = new HashMap<>();


    public String addHotel(Hotel hotel){
        if(hoteldb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hoteldb.put(hotel.getHotelName(), hotel);

        return "SUCCESS";
    }

    public Integer addUser(User user) {
        userdb.put(user.getaadharCardNo(), user);
        //to handle null
        PersonBookingdb.put(user.getaadharCardNo(), new ArrayList<>());

        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities(){
        Hotel mostfacilitiesHotel=null;
        int maxsize = 0;
        for(String hotelname : hoteldb.keySet()){

            List<Facility>hotelList = hoteldb.get(hotelname).getFacilities();


            if(mostfacilitiesHotel==null || hotelList.size() > maxsize){
                mostfacilitiesHotel = hoteldb.get(hotelname);
                maxsize = hotelList.size();
            }
            else if (hotelList.size()==maxsize) {
                //lexographical compoarison

                int comparison = hotelname.compareTo(mostfacilitiesHotel.getHotelName());
                if (comparison < 0) {
                    mostfacilitiesHotel = hoteldb.get(hotelname);
                }
            }

        }

        if(maxsize==0){
            return "";
        }

        return  mostfacilitiesHotel.getHotelName();
    }

    public int bookARoom(Booking booking) {
        if(booking==null){
            return  -1;
        }
        // Generate a booking ID using UUID
        String bookingID = UUID.randomUUID().toString();

        // Check if the hotel exists in the database
        Hotel hotel = hoteldb.get(booking.getHotelName());
        if (hotel == null) {
            return -1; // Hotel not found
        }

        // Check if there are enough available rooms
        int requestedRooms = booking.getNoOfRooms();
        int availableRooms = hotel.getAvailableRooms();
        if (requestedRooms > availableRooms) {
            return -1; // Not enough rooms available
        }

        // Calculate the total amount to be paid
        int amountPerNight = hotel.getPricePerNight();
        int amountToBePaid = requestedRooms * amountPerNight;

        // Save the booking entity with the generated bookingID as the primary key
        booking.setBookingId(bookingID);
        booking.setAmountToBePaid(amountToBePaid);
        bookingdb.put(bookingID, booking);

        //Add booking id to user booking list
        int user = booking.getBookingAadharCard();
        List<String>list = PersonBookingdb.getOrDefault(user, new ArrayList<>());
        list.add(bookingID);
        PersonBookingdb.put(user, list);

        // Update the available rooms in the hotel
        hotel.setAvailableRooms(availableRooms - requestedRooms);
        hoteldb.put(booking.getHotelName(), hotel);

        return amountToBePaid;
    }

    public int getBooking(Integer aadharCard) {
        if (aadharCard == null || PersonBookingdb == null) {
            return 0; // Handle null Aadhar card or null database gracefully
        }

        if (!PersonBookingdb.containsKey(aadharCard)) {
            return 0; // Aadhar card not found in the database
        }

        List<String> bookingList = PersonBookingdb.get(aadharCard);
        return bookingList.size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        // Get the hotel from the hotelDb
        Hotel hotel = hoteldb.get(hotelName);

        // If the hotel doesn't exist, return null (or handle the error appropriately)
        if (hotel == null) {
            return null; // Hotel not found
        }

        // Get the existing facilities of the hotel
        List<Facility> existingFacilities = hotel.getFacilities();

        // Create a set to keep track of the facilities already added
        Set<Facility> addedFacilities = new HashSet<>(existingFacilities);

        // Iterate through the new facilities and add them to the hotel if they are not already present
        for (Facility newFacility : newFacilities) {
            if (!addedFacilities.contains(newFacility)) {
                existingFacilities.add(newFacility);
                addedFacilities.add(newFacility);
            }
        }

        // Update the facilities in the hotel
        hotel.setFacilities(existingFacilities);

        // Update the hotel in the hotelDb
        hoteldb.put(hotelName, hotel);

        // Return the updated hotel
        return hotel;
    }

}

