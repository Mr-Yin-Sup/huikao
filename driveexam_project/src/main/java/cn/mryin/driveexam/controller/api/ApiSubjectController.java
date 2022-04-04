package cn.mryin.driveexam.controller.api;

import cn.mryin.driveexam.mapper.*;
import cn.mryin.driveexam.pojo.*;
import cn.mryin.driveexam.service.*;
import cn.mryin.driveexam.utils.RedisUtil;
import cn.mryin.driveexam.utils.TxSendSmsUtil;
import cn.mryin.driveexam.utils.VerCodeGenerateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Function;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author MrYin
 * @date 2021/11/17
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Log4j2
public class ApiSubjectController {



    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    SubjectProportionService subjectProportionService;

    @Autowired
    IcnoTypeService icnoTypeService;

    @Autowired
    IcnoService icnoService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    ErrSubjectMapper errSubjectMapper;

    @Autowired
    FBackMapper fBackMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;



    @RequestMapping("/findSubjectByCarAndKm")
    public List<Subject> findSubjectByCarAndKm( @RequestParam String sKm,@RequestParam String sCar,@RequestParam String state,@RequestParam String errList){
        Subject subject=new Subject();
        subject.setsKm(sKm);
        subject.setsCar(sCar);

        List<Subject> subjectByCarAndKm;
        if(state.equals("cuoti")){

            String[] split = errList.split(",");
            subjectByCarAndKm = subjectSevice.findSubjectByAllId(split);
        }else{
            subjectByCarAndKm = subjectSevice.findSubjectByCarAndKm(subject);
        }

        return subjectByCarAndKm;

    }

    //查找考试设置,通过车型和科目
    @PostMapping("/findExamSetByCarAndKm")
    @ResponseBody
    public JSON findExamSetByCarAndKm(  @RequestParam String car,  @RequestParam String km){

        return subjectProportionService.findExamSetByCarAndKm(car,km);
    }

    /**
     *获取题目总数
     * @param sCar 车型
     * @param sKm 科目
     * @return
     */
    @RequestMapping("/getSubjectTotalByCarAndKm")
    public int getSubjectTotalByCarAndKm( String sCar, String sKm ){
        int total = subjectSevice.findSubjectTotalByCarAndKm(sCar, sKm);
        return total;
    }

    /**
     * 查询所有顶级图标分类
     */
    @RequestMapping("/findTopIcnoSType")
    public  JSON findTopIcnoSType(){
        JSONObject jsonObject=new JSONObject();
        List<IcnoType> topIcnoSType = icnoTypeService.findTopIcnoSType();

        //List<Icno> icnoTypeTop4Img = icnoService.findIcnoTypeTop4Img();
        jsonObject.put("top",topIcnoSType);
        //jsonObject.put("top4img",icnoTypeTop4Img);
        return  jsonObject;
    }

    /**
     * 通过类别id查询图标
     */
    @RequestMapping("/findIcnoByTypeId")
    public  List<Icno> findIcnoByTypeId(@RequestParam String typeId){

        return icnoService.findByTypeId(Integer.parseInt(typeId));
    }

    /**
     * 查询图标的子集分类
     */
    @RequestMapping("/findIcnoTypeByparent")
    public JSON findIcnoTypeByparent(@RequestParam int id){
        JSONObject jsonObject=new JSONObject();

        jsonObject.put("top",icnoTypeService.findChildrenByparent(id));
        return jsonObject;
    }

    /**
     * 用户注册
     */
        @RequestMapping("/userRegister")
    public AjaxResult userRegister(@RequestBody User user,@RequestParam String phoneCode){
        int insert=0;
        String redisCode =String.valueOf(redisUtil.get(user.getuPhone() + "-code"));

        if(!redisCode.equals(phoneCode)){
            return AjaxResult.error("code error");
        }else{
            redisUtil.del(user.getuPhone()+"-code");
        }

        try {
            user.setuImg("/uploadFile/nullTouxiang.jpg");
            user.setuType("0");
            user.setuSex("1");
            insert = userMapper.insert(user);
        } catch (Exception e) {
            return null;
        }
        if (insert>0){
            return AjaxResult.success(user);
        }
        return AjaxResult.error();
    }


    /**
     * 用户修改密码
     */
    @RequestMapping("/userUpdatePassword")
    public AjaxResult userUpdatePassword(@RequestBody User user,@RequestParam String phoneCode){
        int insert=0;
        String redisCode =String.valueOf(redisUtil.get(user.getuPhone() + "-code"));

        if(!redisCode.equals(phoneCode)){
            return AjaxResult.error("code error");
        }else{
            redisUtil.del(user.getuPhone()+"-code");
        }

        try {
            insert = userMapper.userUpdatePassword(user);
        } catch (Exception e) {
            return null;
        }
        if (insert>0){
            return AjaxResult.success(user);
        }
        return AjaxResult.error();
    }



    //用户登录
    @RequestMapping("/userLogin")
    public User userLogin(@RequestBody User user){
        User login = userMapper.findLogin(user);

        return login;
    }

    @RequestMapping("/checkUserExis")
    public boolean checkUserExis(@RequestParam String uNumber){

        User user = userMapper.checkUserExis(uNumber);

        if (user!=null)return true;
        return false;
    }

    @RequestMapping("/updateUserInfo")
    public User updateUserInfo(@RequestBody User user){

        int i = userMapper.updateByPrimaryKey(user);
        if (i>0)return userMapper.selectByPrimaryKey(user.getuId());
        return null;
    }

    @RequestMapping("/userModPassword")
    public User userModPassword(@RequestParam String password ,@RequestParam String uid){

        int i = userMapper.updatePassword(uid, password);
        if (i>0){
            return userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        }
        return null;
    }

    @PostMapping("/userSubmitScore")
    public String userSubmitScore(@RequestBody Map<String, String> map){


        Score score = Score.builder()
                .uId(Integer.valueOf(map.get("uId")))
                .km(map.get("km"))
                .carType(map.get("carType"))
                .score(Byte.valueOf(map.get("score")))
                .useTime(ApiSubjectController.transferDateFormat( map.get("useTime")))
                .examTime(ApiSubjectController.transferDateFormat(map.get("examTime")))
                .successCount(Byte.valueOf(map.get("successCount")))
                .errorCount(Byte.valueOf(map.get("errorCount")))
                .unan(Byte.valueOf(map.get("unan")))
                .build();
        int insert = scoreMapper.insert(score);
        if(insert>0){
            return "ok";
        }
        return  null;
    }


    /**
     * 获取用户考试成绩
     */
    @RequestMapping("/getUserScores")
    public List<Score> getUserScores(@RequestBody Score score){

        List<Score> scoresByUserAndKmAndCar = scoreMapper.findScoresByUserAndKmAndCar(score);
        return scoresByUserAndKmAndCar;
    }


    /**
     * 修改用户的收藏
     */
    @RequestMapping("/updateUserCollection")
    public String updateUserCollection(@RequestParam String uId,@RequestParam String uCollection){
        int i = userMapper.updateUserCollection(uId, uCollection);
        if (i>0)return "success";
        return "error";
    }


    /**
     * 用户提交错题反馈
     */
    @RequestMapping("/postErrorSubjectFB")
    public String postErrorSubjectFB(@RequestParam int sId,@RequestParam int uId,@RequestParam String errMsg){
        ErrSubject errSubject=new ErrSubject();
        errSubject.setErrMsg(errMsg);
        errSubject.setUId(uId);
        errSubject.setSId(sId);
        int insert = errSubjectMapper.insert(errSubject);

        if (insert>0)return "success";
        return "error";
    }

    /**
     * 用户反馈
     */
    @RequestMapping("/postFBack")
    public String postFBack(@RequestParam int uId,@RequestParam String msg){
        FBack fBack = FBack.builder().uId(uId).fBackMsg(msg).build();
        int insert = fBackMapper.insert(fBack);
        if (insert>0)return "success";
        return "error";

    }

    /**
     * 发送短信验证码
     * @param phoneNumber 发送到的手机号码
     * @return
     */
    @RequestMapping("/sendPhoneCode")//发送短信验证码
    public String sendPhoneCode( String phoneNumber){
        int time=5;//验证码失效时间(分钟)
        //套餐过期改使用六个9验证
        //String code = VerCodeGenerateUtil.generateVerCode();
        String code = "999999";
        String redisCode= (String) redisUtil.get(phoneNumber+"-code");
        System.out.println(redisCode);
        if (redisCode!=null){//如果redis中有验证码
            long expire = redisUtil.getExpire(phoneNumber + "-code");
            System.out.println("过期时间"+expire);
            if (expire>time*60-60){//若果如果验证码是一分钟之内发送的不会继续发送
                return "no";
            }
        }

        redisUtil.set(phoneNumber+"-code",code,time*60);//设置redis中存储验证码
        phoneNumber="+86"+phoneNumber;
        String[] templateParamSet1 = new String[]{
                code,//对用模板{1]变量
                String.valueOf(time)};//对应模板{2]变量
        String[] phoneNumberSet1 = new String[]{phoneNumber};//需要在号码前加入”+86“，可在工具类中处理

        try{
        String s = TxSendSmsUtil.txSendSms(phoneNumberSet1, templateParamSet1);//调用返回是字符串
         System.out.println(s);
        return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "no";
        }
    }

    /**
     * 效验手机号码有没有注册过
     * @param phoneNumber 手机号码
     * @return
     */
    @RequestMapping("/checkPhoneNumberIsRegister")
    public String checkPhoneNumberIsRegister(@RequestParam String phoneNumber){
        return userService.checkPhoneNumberIsRegister(phoneNumber);
    }

    /**
     * 将2019-06-03T16:00:00.000Z日期格式转换为2019-06-03 16:00:00格式
     * @param oldDateStr
     * @return
     */
    public static Date transferDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)){
            return null;
        }
        Date date = null;
        Date date1 = null;
        String dateStr = null;
        try {
            dateStr = oldDateStr.replace("Z", " UTC");//是空格+UTC
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date1 = df.parse(dateStr);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date = df1.parse(date1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
