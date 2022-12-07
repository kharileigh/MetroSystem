/**
 *
 * @author kharileigh
 */

package com.five.model.persistence;

import com.five.entity.MetroSystem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentralMetroSystemDao extends JpaRepository<MetroSystem, Integer> {
    
    // INPUT - userId 
    // RETURNS - List of Full Metro System Objects
    public List<MetroSystem> searchMetroSystemByUserId(int userId);
    
}
