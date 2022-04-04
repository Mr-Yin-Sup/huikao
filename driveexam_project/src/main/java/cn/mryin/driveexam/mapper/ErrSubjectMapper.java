package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.ErrSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/7
 */
@Mapper
@Repository
public interface ErrSubjectMapper {
   int insert(ErrSubject subject);

   List<ErrSubject> findAll();

   int handleState(@Param("state") int state, @Param("id") int id);

   int handleStateBySId(@Param("state") int state, @Param("sId") int sId);

   int handelCheck(int[] checks);
}
