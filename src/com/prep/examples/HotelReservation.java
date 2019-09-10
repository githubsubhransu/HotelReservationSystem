/**
 * This program created for presentation only. Contains solution to a specific
 * hotel reservation scenario with given test cases and solutions.
 */

package com.prep.examples;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Subhransu Mishra
 * @version 1.0
 * @since 09.09.19
 * @param
 * @exception IllegalArgumentException
 */

public class HotelReservation {

    static final int maxDays = 31;

    // Main Method which drives the entire solution and also covers scanner parameter exceptions
    public static void main(String[] args) throws IllegalArgumentException{
        System.out.println("Please Input Number Of Rooms In The Hotel : \n");
        Scanner scanner = new Scanner(System.in);
        int maxRooms = 0;
        try {
            maxRooms = scanner.nextInt();
        }catch (Exception e){
            throw new IllegalArgumentException("Please pass integer range of values in input between 1 to 2000");
        }
        if((maxRooms < 1) || (maxRooms > 2000)){
            throw new IllegalArgumentException("Invalid room numbers : Must be between 1 to 2000");
        }
        int[][] hotelRoomMatrix = new int[maxRooms][maxDays];

        if (doTestsPass(hotelRoomMatrix,maxRooms)){
            System.out.println("All Test Cases Passed Successfully");
        }else {
            System.out.println("Testing is not successful");
        }
    }

    // roomReservationInitiator method determines if a reservation is possible or not followed by
    // registering the room in the matrix system.
    public static Boolean roomReservationInitiator(int[][] hotelRoomMatrix,int maxRooms,int startDay,int endDay){

        Boolean hotelBooked = false;
        // Validate if the input dates are less than 0
        if(startDay<0 || endDay<0){
            return false;
        }
        // Validate if the Start and End date are not exceeding 31
        if(startDay > 31 || endDay > 31){
            return false;
        }
        // Validate if the gap of reservation is more than a month
        if(endDay-startDay > 31){
            return false;
        }
        // Loop through the existing Hotel Matrix to check availability
        for(int i=0;i<maxRooms;i++){
            int counter = 0;
            for(int j=startDay;j<=endDay;j++){
                if (hotelRoomMatrix[i][j] > -1){
                    break;
                }else {
                    counter += 1;
                }
            }
            // Validate the counter against availability of the rooms, then proceed for reservation.
            // If reservation is successful then leave the loop
            if (counter == (endDay-startDay+1)){
                int j = startDay;
                while (j<=endDay){
                    hotelRoomMatrix[i][j] = 1;
                    j+=1;
                }
                hotelBooked = true;
                break;
            }
        }
        return hotelBooked;
    }

    // doTestsPass : Performs all the Unit Testings required to prove the solution
    public static boolean doTestsPass(int[][] hotelRoomMatrix, int maxRooms)
    {
        boolean result = true;
        resetReservationMatrix(hotelRoomMatrix);
        // Base test case 1a : Negetive Start Date
        result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,-4,2) == false;
        resetReservationMatrix(hotelRoomMatrix);
        // Base test case 1b : Negetive Start Date
        result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,200,400) == false;
        resetReservationMatrix(hotelRoomMatrix);
        // Base test case Extra 1c : Start and End Dates are beyond 31 days
        result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,40,4) == false;
        resetReservationMatrix(hotelRoomMatrix);
        result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,4,40) == false;
        resetReservationMatrix(hotelRoomMatrix);
        switch (maxRooms){
            case 3:
                // Request are accepted 2 : All Test cases
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,0,5) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,7,13) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,3,9) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,5,7) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,6,6) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,0,4) == true;
                resetReservationMatrix(hotelRoomMatrix);
                // Requests are Denied 3 : All Test cases
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,1,3) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,2,5) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,1,9) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,0,15) == false;
                resetReservationMatrix(hotelRoomMatrix);
                // Requests can be accepted after a decline 4 : All Test cases
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,1,3) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,0,15) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,1,9) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,2,5) == false;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,4,9) == true;
                break;
            case 2:
                // Complex Requests 5 : All test cases
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,1,3) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,0,4) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,2,3) == false;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,5,5) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,4,10) == false;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,10,10) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,6,7) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,8,10) == true;
                result &= roomReservationInitiator(hotelRoomMatrix,maxRooms,8,9) == true;
                break;
            default:
                System.out.println("For selected number of rooms (" +maxRooms+ ") Only Base cases are covered till now");
                break;
        }
        return result;
    }

    // resetReservationMatrix method will initialize the hotel matrix with negative values in all cells based on size
    public static void resetReservationMatrix(int[][] hotelRoomMatrix ){
        for (int[] row: hotelRoomMatrix){
            Arrays.fill(row,-1);
        }
    }
}
