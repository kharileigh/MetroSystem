/**
 *
 * @author kharileigh, priyanka, victoria, stephanie, lidija
 */
package com.five.entity;

import java.util.List;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationList {
    
    private List<Station> stations;
}
