
package com.five.persistence;

import com.five.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author priyankapatel
 */
@Repository
public interface UserDao extends JpaRepository< User, Integer>{
    
    public User getUserByUserIdAndUserPassword(int userId,String userPassword);
   
    public User getUserByUserNameAndUserPassword(String userName, String userPassword);

    public User getUserByUserName(String userName);
 
}

