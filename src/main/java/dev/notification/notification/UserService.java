package dev.notification.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> allUsers(){
        return _userRepository.findAll();
    }
    public Optional<User> singleUser(Long UserId){
        return Optional.ofNullable(_userRepository.findByUserId(UserId).orElseThrow(() -> new RuntimeException()));
    }

    public User createUser(
            Long userId,
            String name,
            String contacts,
            String email,
            String password
    ){User newUser = _userRepository.insert(
                new User(userId, name, contacts, email, password)
        );
        return newUser;
    }

    public Optional<User> login(String email, String password) {
       Query query = new Query(Criteria.where("email").is(email).and("password").is(password));
       User user = mongoTemplate.findOne(query, User.class);
       return Optional.ofNullable(user);
    }
}
