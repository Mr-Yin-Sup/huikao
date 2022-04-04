package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.FBack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/9
 */
@Mapper
@Repository
public interface FBackMapper {
    int insert(FBack fBack);

    List<FBack> findAll();

    int handleState(@Param("state") int state,@Param("id") int id);

    int handelCheck(int[] checks);

}
