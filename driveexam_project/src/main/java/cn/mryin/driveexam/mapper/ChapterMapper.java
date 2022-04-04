package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChapterMapper {
    int deleteByPrimaryKey(Integer chapterId);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(Integer chapterId);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);
    List<Chapter> findByKm(String id);
    List<Chapter> findAll();

    List<Chapter> findAllOnly();
}