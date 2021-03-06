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

    //??????????????????,?????????????????????
    @PostMapping("/findExamSetByCarAndKm")
    @ResponseBody
    public JSON findExamSetByCarAndKm(  @RequestParam String car,  @RequestParam String km){

        return subjectProportionService.findExamSetByCarAndKm(car,km);
    }

    /**
     *??????????????????
     * @param sCar ??????
     * @param sKm ??????
     * @return
     */
    @RequestMapping("/getSubjectTotalByCarAndKm")
    public int getSubjectTotalByCarAndKm( String sCar, String sKm ){
        int total = subjectSevice.findSubjectTotalByCarAndKm(sCar, sKm);
        return total;
    }

    /**
     * ??????????????????????????????
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
     * ????????????id????????????
     */
    @RequestMapping("/findIcnoByTypeId")
    public  List<Icno> findIcnoByTypeId(@RequestParam String typeId){

        return icnoService.findByTypeId(Integer.parseInt(typeId));
    }

    /**
     * ???????????????????????????
     */
    @RequestMapping("/findIcnoTypeByparent")
    public JSON findIcnoTypeByparent(@RequestParam int id){
        JSONObject jsonObject=new JSONObject();

        jsonObject.put("top",icnoTypeService.findChildrenByparent(id));
        return jsonObject;
    }

    /**
     * ????????????
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
     * ??????????????????
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



    //????????????
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
     * ????????????????????????
     */
    @RequestMapping("/getUserScores")
    public List<Score> getUserScores(@RequestBody Score score){

        List<Score> scoresByUserAndKmAndCar = scoreMapper.findScoresByUserAndKmAndCar(score);
        return scoresByUserAndKmAndCar;
    }


    /**
     * ?????????????????????
     */
    @RequestMapping("/updateUserCollection")
    public String updateUserCollection(@RequestParam String uId,@RequestParam String uCollection){
        int i = userMapper.updateUserCollection(uId, uCollection);
        if (i>0)return "success";
        return "error";
    }


    /**
     * ????????????????????????
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
     * ????????????
     */
    @RequestMapping("/postFBack")
    public String postFBack(@RequestParam int uId,@RequestParam String msg){
        FBack fBack = FBack.builder().uId(uId).fBackMsg(msg).build();
        int insert = fBackMapper.insert(fBack);
        if (insert>0)return "success";
        return "error";

    }

    /**
     * ?????????????????????
     * @param phoneNumber ????????????????????????
     * @return
     */
    @RequestMapping("/sendPhoneCode")//?????????????????????
    public String sendPhoneCode( String phoneNumber){
        int time=5;//?????????????????????(??????)
        //???????????????????????????9??????
        //String code = VerCodeGenerateUtil.generateVerCode();
        String code = "999999";
        String redisCode= (String) redisUtil.get(phoneNumber+"-code");
        System.out.println(redisCode);
        if (redisCode!=null){//??????redis???????????????
            long expire = redisUtil.getExpire(phoneNumber + "-code");
            System.out.println("????????????"+expire);
            if (expire>time*60-60){//??????????????????????????????????????????????????????????????????
                return "no";
            }
        }

        redisUtil.set(phoneNumber+"-code",code,time*60);//??????redis??????????????????
        phoneNumber="+86"+phoneNumber;
        String[] templateParamSet1 = new String[]{
                code,//????????????{1]??????
                String.valueOf(time)};//????????????{2]??????
        String[] phoneNumberSet1 = new String[]{phoneNumber};//???????????????????????????+86??????????????????????????????

        try{
        String s = TxSendSmsUtil.txSendSms(phoneNumberSet1, templateParamSet1);//????????????????????????
         System.out.println(s);
        return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "no";
        }
    }

    /**
     * ????????????????????????????????????
     * @param phoneNumber ????????????
     * @return
     */
    @RequestMapping("/checkPhoneNumberIsRegister")
    public String checkPhoneNumberIsRegister(@RequestParam String phoneNumber){
        return userService.checkPhoneNumberIsRegister(phoneNumber);
    }

    /**
     * ???2019-06-03T16:00:00.000Z?????????????????????2019-06-03 16:00:00??????
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
            dateStr = oldDateStr.replace("Z", " UTC");//?????????+UTC
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
