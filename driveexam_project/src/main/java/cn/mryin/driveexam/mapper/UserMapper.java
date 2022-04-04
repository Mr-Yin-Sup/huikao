package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findLogin(User user);

    /**
     * 判断用户存在
     * @param uNumber 用户账号
     * @return 是否存在
     */
    User checkUserExis(@Param("uNumber") String uNumber);

    /**
     * 修改用户密码
     * @param uid
     * @param password
     * @return
     */
    int updatePassword(@Param("uId") String uid,@Param("uPassword") String password);

    /**
     *
     */
    int updateUserCollection(@Param("uId") String uid,@Param("uCollection") String uCollection);


    List<User> findAll(@Param("uName") String uName);

    int setUserState(@Param("uId") int uId,@Param("uState") String uState);

    int checkPhoneNumberIsRegister(@Param("phoneNumber") String phoneNumber) ;

    int userUpdatePassword(User user);
}