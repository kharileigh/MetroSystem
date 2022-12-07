/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetroSystemList {
    
    List<MetroSystem> fares;
            
}
