package com.zzq.zzqvhr.controller;

import com.zzq.zzqvhr.model.Hr;
import com.zzq.zzqvhr.model.Role;
import com.zzq.zzqvhr.repository.RoleRepository;
import com.zzq.zzqvhr.service.HrService;
import com.zzq.zzqvhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HrController {
    @Autowired
    private RoleService roleService;
    private HrService hrService;

    @Autowired
    public void setHrService(HrService hrService){
        this.hrService = hrService;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }




    /**
     * 添加hr
     * @param hr
     * @return
     */
    @PostMapping("/admin/addhr")
    public String addHr(@RequestBody Hr hr){
        hrService.insertHr(hr);
        return "添加成功";
    }

    /**
     * 根据id删除hr
     * @param id
     * @return
     */
    @DeleteMapping("/admin/hrDelete/{id}")
    public String deleteHr(@PathVariable Integer id){
        hrService.deleteHrById(id);
        return "删除成功";
    }

    /**
     * 根据id更新hr信息
     * @param id
     * @param hr
     * @return
     */
    @RequestMapping("/hr/hrUpdate/{id}")
    public String updateHr(@PathVariable Integer id,@RequestBody Hr hr){
        hrService.updateHrById(id,hr);
        return "更新成功";
    }

    /**
     * 查看所有hr的信息
     * @return
     */
    @GetMapping("/admin/findAllHr")
    public List<Hr> findALLHr(){
        return hrService.getAllHr();
    }
    //得到所有的角色
    @GetMapping("/admin/roles")
    public List<Role> getALLRoles(){
        return roleService.getAllRoles();
    }
    //更改hr角色

}
