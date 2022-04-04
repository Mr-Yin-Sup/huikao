package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.ErrSubjectMapper;
import cn.mryin.driveexam.pojo.ErrSubject;
import cn.mryin.driveexam.pojo.FBack;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrYin
 * @date 2021/12/11
 */
@Service
public class ErrSubjectService {
    @Autowired
    private ErrSubjectMapper errSubjectMapper;

    public PageInfo findAll(int pageNum,int pageSize){//查询所有用户反馈的错题
        PageHelper.startPage(pageNum,pageSize);
        List<ErrSubject> all = errSubjectMapper.findAll();
        PageInfo<ErrSubject> pageInfo = new PageInfo<ErrSubject>(all);

        return pageInfo;
    }

    public int handleState( int state,  int id){
        return errSubjectMapper.handleState(state, id);
    }


    public  int handleStateBySId( int state,  int sId){//通过sId修改状态
        return errSubjectMapper.handleStateBySId(state,sId);
    }


    public String handelCheck(String checks) {//批量修改状态

        String[] split = checks.split(",");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        int i = errSubjectMapper.handelCheck(arr);

        if (i > 0) {
            return "success";

        }
        return "error";
    }

}
