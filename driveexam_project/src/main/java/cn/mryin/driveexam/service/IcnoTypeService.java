package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.IcnoTypeMapper;
import cn.mryin.driveexam.pojo.IcnoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/16
 */
@Service
public class IcnoTypeService {

    @Autowired
    IcnoTypeMapper icnoTypeMapper;

    public List<IcnoType> findAll(){
        return icnoTypeMapper.findAll();
    }

    public List<IcnoType> findTopIcnoSType(){
        return icnoTypeMapper.findTopIcnoSType();
    }

    /**
     * 通过id查询图标顶级分类的子集
     * @param id
     * @return
     */
    public List<IcnoType> findChildrenByparent(int id){

        return icnoTypeMapper.findChildrenByparent(id);
    }

    public int insert(IcnoType icnoType){
        return icnoTypeMapper.insert(icnoType);
    }

    public int deleteIcnoTypeById(int icnoTypeId){
        return icnoTypeMapper.deleteByPrimaryKey(icnoTypeId);
    }

    public int updateByPrimaryKey(IcnoType icnoType){
        return icnoTypeMapper.updateByPrimaryKey(icnoType);
    }
}
