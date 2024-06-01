package base.base_in_spring.domain.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final Map<Long, User> store = new HashMap<>();

    private static long sequence = 0L;

    public User save(User user) {
        user.setId(sequence++);
        store.put(user.getId(), user);
        return user;
    }

    public User findById(Long id) {
        return store.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long userId, User updatedUser) {
        User user = findById(userId);
        user.setName(updatedUser.getName());
        user.setAge(updatedUser.getAge());
    }

    public void clear() {
        store.clear();
    }
}
