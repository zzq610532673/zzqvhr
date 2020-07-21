package com.zzq.zzqvhr.repository;

import com.zzq.zzqvhr.model.Hr;
import com.zzq.zzqvhr.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface HrRepository extends JpaRepository<Hr,Integer>{
    Hr findByUsername(String username);

}
