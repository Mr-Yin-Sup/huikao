package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.KnowPointsMapper;
import cn.mryin.driveexam.pojo.KnowPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/11/6
 */
@Service
public class KnowPointsService {
    @Autowired
    KnowPointsMapper knowPointsMapper;

    public List<KnowPoints> findAll(){
        return  knowPointsMapper.findAll();
    }

    public String addKnowPoint(KnowPoints knowPoints){
        int insert = knowPointsMapper.insert(knowPoints);
        if (insert>0)return "success";
        return "error";

    }

    public String deleteKnowPoint(int knowId){
        int i = knowPointsMapper.deleteByPrimaryKey(knowId);
        if (i>0)return "success";
        return "error";

    }


    public String modKnowPoint(KnowPoints knowPoints){
        int i = knowPointsMapper.updateByPrimaryKey(knowPoints);
        if (i>0)return "success";
        return "error";

    }
}
