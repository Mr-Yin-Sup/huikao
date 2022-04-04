package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.KnowPoints;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KnowPointsMapper {
    int deleteByPrimaryKey(Integer knowId);

    int insert(KnowPoints record);

    int insertSelective(KnowPoints record);

    KnowPoints selectByPrimaryKey(Integer knowId);

    int updateByPrimaryKeySelective(KnowPoints record);

    int updateByPrimaryKey(KnowPoints record);

    List<KnowPoints> findAll();
}