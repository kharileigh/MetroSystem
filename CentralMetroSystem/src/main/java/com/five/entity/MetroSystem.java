/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CentralMetroSystem") 
public class MetroSystem {
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int fareId;
    private int userId;
    private double starterBalance;
    private double remainingBalance;
    private double price;
    private String sourceStation;
    private String destinationStation;
    private LocalDate sourceSwipeInDateAndTime;
    private LocalDate destinationSwipeOutDateAndTime;
    
}
