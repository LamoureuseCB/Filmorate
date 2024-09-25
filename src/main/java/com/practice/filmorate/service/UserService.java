package com.practice.filmorate.service;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId,int friendId) {
        User user = userStorage.getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.add(friendId);
        user.setFriends(friends);

 }
    public void removeFriend(int userId,int friendId){
        User user = userStorage.getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.remove(friendId);
        user.setFriends(friends);

    }
    public List<User> getAllFriends(int userId){
        User user = userStorage.getUserById(userId);
        List<User> friendList = new ArrayList<>();
        Set<Integer> friends = user.getFriends();
        for (Integer friend : friends) {
            friendList.add(userStorage.getUserById(friend));
        }
        return friendList;
    }

}
//Создайте UserService, который будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
//Подсказка: про список друзей и лайки
//Есть много способов хранить информацию о том, что два пользователя являются друзьями. Например,
// можно создать свойство friends в классе пользователя, которое будет содержать список его друзей.
// Вы можете использовать такое решение или придумать своё. Для того чтобы обеспечить уникальность значения (мы не можем добавить одного человека в друзья дважды), проще всего использовать для хранения Set<Long> c id друзей. Таким же образом можно обеспечить условие «один пользователь — один лайк» для оценки фильмов.