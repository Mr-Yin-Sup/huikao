package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.IcnoMapper;
import cn.mryin.driveexam.pojo.Icno;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/15
 */
@Service
public class IcnoService {
    @Autowired
    IcnoMapper icnoMapper;

    public PageInfo findAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Icno> all = icnoMapper.findAll();

        PageInfo<Icno> pageInfo = new PageInfo<Icno>(all);

        return pageInfo;
    }

    public String addIcno(Icno icno){
        int insert = icnoMapper.insert(icno);
        if (insert>0)return "success";
        return "error";
    }

    public String delIcno(int icnoId){
        int insert = icnoMapper.deleteByPrimaryKey(icnoId);
        if (insert>0)return "success";
        return "error";
    }

    public String modIcno(Icno icno){
        int i = icnoMapper.updateByPrimaryKey(icno);
        if (i>0)return "success";
        return "error";
    }


    public PageInfo seachIcno(int pageNum,int pageSize, Icno icno){
        PageHelper.startPage(pageNum,pageSize);
        List<Icno> all = icnoMapper.seachIcno(icno);
        PageInfo<Icno> pageInfo = new PageInfo<Icno>(all);

        return pageInfo;
    }

    /**
     *查询顶级分类交通标志大全的前四张图片
     */
    public List<Icno> findIcnoTypeTop4Img(){
        return  icnoMapper.findIcnoTypeTop4Img();
    }

    /**
     * 通过类别查询
     * @param id 类别id
     * @return
     */
    public List<Icno> findByTypeId(int id){
        return icnoMapper.findByTypeId(id);
    }
}
