package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubjectMapper {

    int deleteByPrimaryKey(Integer sId);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);

    List<Subject> findAllByCarAndKm(String car,String km);

    List<Subject> findAll();

    int deleteCheck(int[] checks);

    int modSubjectState(String sId,String state);

    List<Subject> seachSubject(Subject seach);

    //查询题目,通过章节id
    List<Subject> findSubjectByChapterId (int chpterId);


    //查询题目通过车型和科目
    List<Subject> findSubjectByCarAndKm(Subject subject);

    //查询题目通过题目数组
    List<Subject> findSubjectByAllId(String[] array);

    //<!--查询题目总数,通过车型和科目-->
   int findSubjectTotalByCarAndKm(@Param("sCar") String sCar, @Param("sKm") String sKm);

    //批量插入题目通过Excel生成的list
    int insertByExcele(List<Subject> subjects);

    //通过问题查询题目
    List<Subject> selectLikeQuestion(String question);
}