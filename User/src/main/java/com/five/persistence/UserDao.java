/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.five.persistence;

import com.five.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author priyankapatel
 */
@Repository
public interface UserDao extends JpaRepository< User ,Integer>{
   public User getUserByUserIdAndUserPassword(int userId,String userPassword);
 
}

