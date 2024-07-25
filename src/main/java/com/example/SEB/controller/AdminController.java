package com.example.SEB.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SEB.dto.BillDto;
import com.example.SEB.dto.HouseDto;
import com.example.SEB.dto.SignUpRequest;
import com.example.SEB.dto.UserDto;
import com.example.SEB.enums.Status;
import com.example.SEB.service.BillService;
import com.example.SEB.service.HouseService;
import com.example.SEB.service.UserService;
import com.example.SEB.service.auth.AuthSerrvice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class AdminController {

    private final HouseService houseService;
    private final BillService billService;
    private final UserService userService;
    private final AuthSerrvice authService;
   

   // Create or update a house
   @PostMapping("/houses")
   public ResponseEntity<HouseDto> createHouse(@RequestBody HouseDto houseDto) {
       HouseDto savedHouse = houseService.saveHouse(houseDto);
       return new ResponseEntity<>(savedHouse, HttpStatus.CREATED);
   }

   // Retrieve a house by its ID
   @GetMapping("/houses/{houseId}")
   public ResponseEntity<HouseDto> getHouseById(@PathVariable int houseId) {
       Optional<HouseDto> houseDto = houseService.getHouseById(houseId);
       return houseDto.map(ResponseEntity::ok)
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
   }

   // Retrieve all houses
   @GetMapping("/houses")
   public ResponseEntity<List<HouseDto>> getAllHouses() {
       List<HouseDto> houses = houseService.getAllHouses();
       return new ResponseEntity<>(houses, HttpStatus.OK);
   }

   // Delete a house by its ID
   @DeleteMapping("/houses/{houseId}")
   public ResponseEntity<Void> deleteHouse(@PathVariable int houseId) {
       try {
           houseService.deleteHouse(houseId);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (RuntimeException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

   // Find a house by its meter number
   @GetMapping("/houses/meter-number/{meterNumber}")
   public ResponseEntity<HouseDto> findHouseByMeterNumber(@PathVariable String meterNumber) {
       Optional<HouseDto> houseDto = houseService.findHouseByMeterNumber(meterNumber);
       return houseDto.map(ResponseEntity::ok)
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
   }

   // Find all houses by city
   @GetMapping("/houses/city/{city}")
   public ResponseEntity<List<HouseDto>> findHousesByCity(@PathVariable String city) {
       List<HouseDto> houses = houseService.findHousesByCity(city);
       return new ResponseEntity<>(houses, HttpStatus.OK);
   }

   // Find all houses by state
   @GetMapping("/houses/state/{state}")
   public ResponseEntity<List<HouseDto>> findHousesByState(@PathVariable String state) {
       List<HouseDto> houses = houseService.findHousesByState(state);
       return new ResponseEntity<>(houses, HttpStatus.OK);
   }

   // Find all houses by user ID
   @GetMapping("/houses/user/{userId}")
   public ResponseEntity<List<HouseDto>> findHousesByUserId(@PathVariable Integer userId) {
       List<HouseDto> houses = houseService.findHousesByUserId(userId);
       return new ResponseEntity<>(houses, HttpStatus.OK);
   }

    // Find all houses by owner
   @GetMapping("/owner/{ownerName}")
    public ResponseEntity<List<HouseDto>> getHousesByOwnerName(@PathVariable String ownerName) {
        List<HouseDto> houses = houseService.findByOwnerName(ownerName);
        return ResponseEntity.ok(houses);
    }

    // Bill endpoints

   // Create or update a bill
    @PostMapping("/bills")
    public ResponseEntity<BillDto> createOrUpdateBill(@RequestBody BillDto billDto) {
        BillDto savedBill = billService.saveBill(billDto);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    // Retrieve a bill by its ID
    @GetMapping("/bills/{billId}")
    public ResponseEntity<BillDto> getBillById(@PathVariable int billId) {
        Optional<BillDto> billDto = billService.getBillById(billId);
        return billDto.map(ResponseEntity::ok)
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Retrieve all bills
    @GetMapping("/bills")
    public ResponseEntity<List<BillDto>> getAllBills() {
        List<BillDto> bills = billService.getAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Delete a bill by its ID
    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<Void> deleteBill(@PathVariable int billId) {
        try {
            billService.deleteBill(billId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find bills by status
    @GetMapping("/bills/status/{status}")
    public ResponseEntity<List<BillDto>> findBillsByStatus(@PathVariable Status status) {
        List<BillDto> bills = billService.findBillsByStatus(status);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Find bills by house ID
    @GetMapping("/bills/house/{houseId}")
    public ResponseEntity<List<BillDto>> findBillsByHouseId(@PathVariable int houseId) {
        List<BillDto> bills = billService.findBillsByHouseId(houseId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Find bills by date range
    @GetMapping("/bills/date-range")
    public ResponseEntity<List<BillDto>> findBillsByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<BillDto> bills = billService.findBillsByDateRange(startDate, endDate);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Find bills by due date
    @GetMapping("/bills/due-date/{dueDate}")
    public ResponseEntity<List<BillDto>> findBillsByDueDate(@PathVariable Date dueDate) {
        List<BillDto> bills = billService.findBillsByDueDate(dueDate);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    // Find bills by paid date
    @GetMapping("/bills/paid-date/{paidDate}")
    public ResponseEntity<List<BillDto>> findBillsByPaidDate(@PathVariable Date paidDate) {
        List<BillDto> bills = billService.findBillsByPaidDate(paidDate);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

     // Create or update a user
     // Create or update a user
     @PostMapping("/users/updateUser")
     public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
         UserDto savedUser = userService.updateUser(userDto);
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
     }
 
     // Retrieve a user by its ID
     @GetMapping("/users/{userId}")
     public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
         Optional<UserDto> userDto = userService.getUserById(userId);
         return userDto.map(ResponseEntity::ok)
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
     }
 
     // Retrieve all users
     @GetMapping("/users")
     public ResponseEntity<List<UserDto>> getAllUsers() {
         List<UserDto> users = userService.getAllUsers();
         return new ResponseEntity<>(users, HttpStatus.OK);
     }
 
     // Delete a user by its ID
     @DeleteMapping("/users/{userId}")
     public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
         try {
             userService.deleteUser(userId);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (RuntimeException e) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }
 
     // Find a user by username
     @GetMapping("/users/username/{username}")
     public ResponseEntity<UserDto> findUserByUsername(@PathVariable String username) {
         Optional<UserDto> userDto = userService.findByUsername(username).map(userService::convertToDto);
         return userDto.map(ResponseEntity::ok)
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
     }
 
     // Sign up a user
     @PostMapping("/createUser")
     public ResponseEntity<String> signupUser(@RequestBody SignUpRequest signupRequest) {
         if (authService.hasUserEmail(signupRequest.getEmail())) {
             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
         }
         UserDto createdUser = authService.signupUser(signupRequest);
 
         if (createdUser == null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
         }
         return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
     }
}