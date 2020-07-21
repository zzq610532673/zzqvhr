package com.zzq.zzqvhr.service;

import com.zzq.zzqvhr.model.Hr;

import com.zzq.zzqvhr.repository.HrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public class HrService implements UserDetailsService {
    @Autowired
    public HrRepository hrRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Hr hr1 = hrRepository.findByUsername(username);
        if(hr1 == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
       // hr.setRoles(hrRepository.getHrRolesById(hr.getId()));
        return hr1;
    }

    //查询所有hr
    public List<Hr> getAllHr(){
        return hrRepository.findAll();
    }

    //根据id删除hr
    public void deleteHrById(Integer id){
        hrRepository.deleteById(id);
    }

    //增加hr
    public void insertHr(Hr hr){
        hrRepository.save(hr);
    }

    //根据id更新hr
    public void updateHrById(Integer id,Hr hr){
        Hr hr1 = hrRepository.findById(id).get();
        if (hr1 != null){
            hr1.setName(hr.getName());
            hr1.setAddress(hr.getAddress());
            hr1.setTelephone(hr.getTelephone());
            hr1.setUsername(hr.getUsername());
            hr1.setPassword(hr.getPassword());
        }
        hrRepository.save(hr1);
    }


}
