package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.UserMapper;
import cn.mryin.driveexam.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/12
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public PageInfo findAll(int pageNum,int pageSize,String uName){

        PageHelper.startPage(pageNum,pageSize);
        List<User> all = userMapper.findAll(uName);
        PageInfo<User> pageInfo = new PageInfo<User>(all);
        return pageInfo;
    }


    /**
     * 修改用户状态
     */
    public int setUserState( int uId, String uState){
        return userMapper.setUserState(uId, uState);
    }

    /**
     * 更改用户信息
     */
    public int updateByPrimaryKey(User user){
        return userMapper.updateByPrimaryKey(user);
    }

    /**
     * 效验手机号码是否注册过
     * @param phoneNumber 手机号码
     * @return
     */
    public String checkPhoneNumberIsRegister(String phoneNumber){
        int i = userMapper.checkPhoneNumberIsRegister(phoneNumber);
        System.out.println(i);
        if (i>0){
            return "no";
        }
        return "ok";
    }
}
