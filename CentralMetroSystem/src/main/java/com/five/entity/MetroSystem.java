/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "") // ADD NAME ONCE DATABASE COMPLETED
public class MetroSystem {
    
    
    @Id
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
