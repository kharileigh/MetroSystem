/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal starterBalance;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainingBalance;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String sourceStation;
    private String destinationStation;
    private String sourceSwipeInDateTime;
    private String destinationSwipeOutDateTime;
    
}
