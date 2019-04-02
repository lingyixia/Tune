package com.feiyu.repository;

import com.feiyu.domain.Artist;
import com.feiyu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * 通过昵称和密码找用户
     */
    public abstract User findByNickNameAndPassWord(String nickName, String passWord);

    /*
更新用户信息
*/
    @Transactional
    @Modifying
    @Query(value = "update User u set u.nickName =?2,u.sex=?3,u.email=?4,u.phone=?5,u.birthday=?6,u.introduce=?7 where u.id = ?1")
    public abstract int updateUser(long id, String nickName, boolean sex, String email, String phone, Date birthday, String introduce);


    /*
    判断某昵称的用户名是否存在
     */
    public abstract User findByNickName(String nickName);

    /*
    返回收藏列表
 */
    public abstract User findByIdEquals(long id);

    /*
    更新收藏列表
    */
    @Transactional
    @Modifying
    @Query(value = "update User u set u.collection =?2 where u.id = ?1")
    public abstract int updateCollection(long id, String collection);
}
