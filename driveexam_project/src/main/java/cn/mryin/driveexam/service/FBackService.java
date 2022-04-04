package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.FBackMapper;
import cn.mryin.driveexam.pojo.FBack;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/10
 */
@Service
public class FBackService {
    @Autowired
    FBackMapper fBackMapper;

    public PageInfo findAll(int pageNum,int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<FBack> all = fBackMapper.findAll();
        PageInfo<FBack> pageInfo = new PageInfo<FBack>(all);

        return pageInfo;
    }

    public int handleState( int state,  int id){
        return fBackMapper.handleState(state, id);
    }


    public String handelCheck(String checks) {//批量修改状态

        String[] split = checks.split(",");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        int i = fBackMapper.handelCheck(arr);

        if (i > 0) {
            return "success";

        }
        return "error";
    }

}
