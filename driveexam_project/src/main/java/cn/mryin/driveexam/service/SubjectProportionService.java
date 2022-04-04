package cn.mryin.driveexam.service;

import cn.mryin.driveexam.mapper.SubjectProportionMapper;
import cn.mryin.driveexam.pojo.SubjectProportion;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author MrYin
 * @date 2021/11/13
 */
@Service
public class SubjectProportionService {
    @Autowired
    SubjectProportionMapper subjectProportionMapper;

    public PageInfo<SubjectProportion> findAll() {
        PageHelper.startPage(1, 100);
        List<SubjectProportion> lists = subjectProportionFormat(subjectProportionMapper.findAll());
        PageInfo<SubjectProportion> pageInfo = new PageInfo<SubjectProportion>(lists);

        return pageInfo;

    }

    public  List<Map<String, String>> findCarAndKm(){
        return subjectProportionMapper.findCarAndKm();
    }

    /**
     * 修改考试设置,通过车型和科目
     * @param ids  修改的id数组
     * @param propors   修改的比例数组,对应上面的id的索引
     * @param car 修改的车型
     * @param km 修改的科目
     * @return
     */
    public String modExamSet( int[] ids,  int[] propors,int car,  int km ){
        List<Map<String,Integer>> list =new ArrayList<>();
        List<Map<String,Integer>> list2 =new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            Map<String,Integer> map=new HashMap<>();
            if (propors[i]==0) {
                map.put("chapter",ids[i]);
                map.put("propor",propors[i]);
                map.put("car",car);
                map.put("km",km);
                list2.add(map);
                continue;
            }
            map.put("chapter",ids[i]);
            map.put("propor",propors[i]);
            map.put("car",car);
            map.put("km",km);
            list.add(map);
            list2.add(map);
         }


        subjectProportionMapper.beforeDelExamSet(list2);
        int i = subjectProportionMapper.modExamSet(list);

        if(i>0)return "success";
        return "error";
    }

   public JSON findExamSetByCarAndKm(String car, String km){

       JSONObject json=new JSONObject();
       List<Map<String, String>> examSetByCarAndKm = subjectProportionMapper.findExamSetByCarAndKm(car, km);

       json.put("data",examSetByCarAndKm);
       return json;
   }

    private List<SubjectProportion> subjectProportionFormat(List<SubjectProportion> list){
        for (SubjectProportion subjectProportion : list) {
            String car=subjectProportion.getCar();
            if (car.equals("1")) car="小车";
            if (car.equals("2")) car="货车";
            if (car.equals("3")) car="客车";
            if (car.equals("4")) car="摩托车";

            subjectProportion.setCar(car);

            String km=subjectProportion.getKm();
            if (km.equals("1")) km="科目一";
            else km="科目四";
            subjectProportion.setKm(km);
        }

        return list;
    }




}
