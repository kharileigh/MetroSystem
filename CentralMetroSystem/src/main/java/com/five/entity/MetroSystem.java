/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Central Metro System") 
public class MetroSystem {
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int fareId;
    private int userId;
    private BigDecimal starterBalance;
    private BigDecimal remainingBalance;
    private BigDecimal price;
    private String sourceStation;
    private String destinationStation;
    private Date sourceSwipeInDateAndTime;
    private Date destinationSwipeOutDateAndTime;
    
}
