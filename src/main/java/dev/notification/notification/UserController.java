package dev.notification.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService _userService;
    @Autowired
    private SequenceService _sequenceService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(_userService.allUsers(), HttpStatus.OK)
    }

    @GetMapping("/{UserId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String UserId){
        Optional<User> user = _userService.singleUser(Long.parseLong(UserId));
        if (user != null) {
            return new ResponseEntity<Optional<User>>(_userService.singleUser(Long.parseLong(UserId)),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> payload)
    {
        long UserId = generateNextUserId("user_sequence");
        User newUser = _userService.createUser(
                UserId,
                payload.get("name"),
                payload.get("contacts"),
                payload.get("email"),
                payload.get("password")
        );

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    private long generateNextUserId(String sequenceName){
        //Get the current sequence value for User IDs
        long sequenceValue = _sequenceService.getSequenceValue(sequenceName);
        //Increment the sequence value and return it
        _sequenceService.incrementSequenceValue(sequenceName);

        return sequenceValue;
    }

//    @RequestMapping(value = "/{UserId}", method = RequestMethod.PUT)
//    public ResponseEntity<User> updateUser(@PathVariable String UserId, @RequestBody User updatedUserInfo){
//        User updatedUser = _userService.updatedUserInformation(Long.parseLong(UserId),updatedUserInfo);
//
//        if (updatedUser != null){
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> payload){
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> user = _userService.login(email, password);
        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



}
