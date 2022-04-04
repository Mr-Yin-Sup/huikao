package cn.mryin.driveexam.mapper;

import cn.mryin.driveexam.pojo.Icno;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IcnoMapper {
    int deleteByPrimaryKey(Integer icnoId);

    int insert(Icno record);

    int insertSelective(Icno record);

    Icno selectByPrimaryKey(Integer icnoId);

    int updateByPrimaryKeySelective(Icno record);

    int updateByPrimaryKey(Icno record);

    List<Icno> findAll();

    List<Icno> seachIcno(Icno icno);

    /**
     *查询顶级分类交通标志大全的前四张图片
     */
    List<Icno> findIcnoTypeTop4Img();

    /**
     * 通过类别查询
     * @param id 类别id
     * @return
     */
    List<Icno>findByTypeId(int id);
}