package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.SubjectProportion;
import cn.mryin.driveexam.pojo.SubjectProportionKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SubjectProportionMapper {
    int deleteByPrimaryKey(SubjectProportionKey key);

    int insert(SubjectProportion record);

    int insertSelective(SubjectProportion record);

    SubjectProportion selectByPrimaryKey(SubjectProportionKey key);

    int updateByPrimaryKeySelective(SubjectProportion record);

    int updateByPrimaryKey(SubjectProportion record);

    List<SubjectProportion> findAll();

    List<Map<String, String>> findCarAndKm();

    List<Map<String, String>> findExamSetByCarAndKm(String car,String km);


    int modExamSet( List<Map<String,Integer>> list);

    /**
     * 在设置考试之前删除
     * @param list
     * @return
     */
    int beforeDelExamSet( List<Map<String,Integer>> list);


}