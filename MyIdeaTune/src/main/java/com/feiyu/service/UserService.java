package com.feiyu.service;

import com.feiyu.domain.User;
import com.feiyu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*
     * 获取所有用户对象
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * 用户注册
     */
    public void register(User user) {
        userRepository.save(user);
    }

    /*
     * 查找需要登录的用户是否存在
     */
    public User findByNickNameAndPassWord(String nickName, String passWord) {
        return userRepository.findByNickNameAndPassWord(nickName, passWord);
    }

    /*
    判断某昵称的用户名是否存在
     */
    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName);
    }

    /*
 更新用户信息
 */
    public int updateUser(User user) {
        return userRepository.updateUser(user.getId(), user.getNickName(), user.getSex(), user.getEmail(), user.getPhone(), user.getBirthday(), user.getIntroduce());
    }

    /*
    * 返回收藏列表
    */
    public User findByIdEquals(long id) {
        return userRepository.findByIdEquals(id);
    }

    /*
    更新收藏列表
    */
    public int updateCollection(long id, String collection) {
        return userRepository.updateCollection(id, collection);
    }
}
