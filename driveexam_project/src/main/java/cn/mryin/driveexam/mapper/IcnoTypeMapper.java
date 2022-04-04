package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.IcnoType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IcnoTypeMapper {
    int deleteByPrimaryKey(Integer icnoTypeId);

    int insert(IcnoType record);

    int insertSelective(IcnoType record);

    IcnoType selectByPrimaryKey(Integer icnoTypeId);

    int updateByPrimaryKeySelective(IcnoType record);

    int updateByPrimaryKey(IcnoType record);

    List<IcnoType> findAll();

    List<IcnoType> findTopIcnoSType();

    /**
     * 通过父级分类查询所有子集
     * @param id
     * @return list
     */
    List<IcnoType> findChildrenByparent(int id);
}