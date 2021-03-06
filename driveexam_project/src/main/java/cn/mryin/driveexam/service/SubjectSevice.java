package cn.mryin.driveexam.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.mryin.driveexam.mapper.ChapterMapper;
import cn.mryin.driveexam.mapper.KnowPointsMapper;
import cn.mryin.driveexam.mapper.SubjectMapper;
import cn.mryin.driveexam.pojo.Chapter;
import cn.mryin.driveexam.pojo.KnowPoints;
import cn.mryin.driveexam.pojo.Subject;
import cn.mryin.driveexam.utils.RedisUtil;
import cn.mryin.driveexam.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author MrYin
 * @date 2021/11/4
 */

@Service
@Log4j2
public class SubjectSevice {
    @Autowired
    SubjectMapper subjectMapper;
    
    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    KnowPointsMapper knowPointsMapper;

    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    RedisUtil redisUtil;
    public String uploadImg(MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            return "????????????";
        }
        //?????????????????????
        //String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadFile\\";

        String path=System.getProperty("user.dir")+"\\uploadFile\\";
        //String path=ClassUtils.getDefaultClassLoader().getResource("").getPath()+ "static/uploadFile/";


        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String fileName = file.getOriginalFilename();  // ?????????
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // ?????????
        fileName = UUID.randomUUID() + suffixName; // ????????????
        File fileImg = new File(path + fileName);


        try {
            file.transferTo(fileImg);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "/uploadFile/" + fileName;//???????????????????????????
    }

    public int addSubject(Subject subject) {

        int insert = subjectMapper.insert(subject);
        subjectSevice.asyncUpdateSubjectCache(subject);//??????????????????

        return insert;

    }

    public JSONObject findAll(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);


        System.out.println("all");
        List<Subject> all1 = subjectMapper.findAll();
        PageInfo<Subject> pageInfo=new PageInfo<>(all1);

        JSONObject json = new JSONObject();
        json.put("total", pageInfo.getTotal());

        all1=this.formatSubjectList(all1);
        json.put("data", all1);
        return json;

    }


    private String getCarString(String number) {
        String str = "";
        for (char c : number.toCharArray()) {
            switch (c) {
                case '1':
                    str += "?????????";
                    break;
                case '2':
                    str += "?????????";
                    break;
                case '3':
                    str += "?????????";
                    break;
                case '4':
                    str += "????????????";
                    break;
            }
        }
        str = str.substring(0, str.length() - 1);

        return str;
    }

    private String getSTypeString(String number) {
        String str = "";

        switch (number) {
            case "1":
                str = "?????????";
                break;
            case "2":
                str = "?????????";
                break;
            case "3":
                str = "?????????";
                break;


        }


        return str;
    }


    private String geKmString(String number) {
        String str = "";

        switch (number) {
            case "1":
                str = "?????????";
                break;
            case "4":
                str = "?????????";
                break;


        }


        return str;
    }


    public String deleteSubject(int sId) {
        int i = subjectMapper.deleteByPrimaryKey(sId);
        subjectSevice.asyncUpdateSubjectCache(subjectSevice.selectOneSubject(sId));
        if (i > 0) return "success";
        return "error";

    }


    public String deleteCheck(String checks) {

        String[] split = checks.split(",");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }

        int i = subjectMapper.deleteCheck(arr);


        if (i > 0) {
            new Thread(() -> {
                  subjectSevice.asyncUpdateSubjectCache(null);//??????????????????
            }).start();
            return "success";

        }
        return "error";
    }

    public Subject selectOneSubject(int sId) {
        return subjectMapper.selectByPrimaryKey(sId);
    }


    public String modSubject(Subject subject) {
        int i = subjectMapper.updateByPrimaryKey(subject);
        this.asyncUpdateSubjectCache(subject);
        if (i > 0) return "success";
        return "error";
    }


    public void asyncUpdateSubjectCache(Subject subject){
        if (subject == null){
            new Thread(() -> {
                String skm;
                String scar;
                for (int i = 0; i < 2; i++) {
                    if (i==0) skm="1";
                    else skm="4";
                    for (int i1 = 1; i1 <= 4; i1++) {
                        String format=MessageFormat.format("subjects::km{0}car{1}", skm,i1);
                        redisUtil.del(format);
                    }

                }

            }).start();

            return;
        }
        new Thread(() -> {
            Chapter chapter;
            if (subject.getChapterId()!=null){
              chapter = chapterMapper.selectByPrimaryKey(subject.getChapterId());
            }else{
                chapter=null;
            }
            String format ;
            for (char c : chapter!=null?chapter.getChapterCar().toCharArray():"1234".toCharArray()) {
                subject.setsCar(String.valueOf(c));
                format=MessageFormat.format("subjects::km{0}car{1}", subject.getsKm(),subject.getsCar());

                redisUtil.del(format);
                subjectSevice.findSubjectByCarAndKm(subject);
            }
        }).start();


    }

    public String modSubjectState(String sId, String state) {
        if (state.equals("0")) {
            state = "1";
        } else state = "0";
        int i = subjectMapper.modSubjectState(sId, state);
        subjectSevice.asyncUpdateSubjectCache(subjectSevice.selectOneSubject(Integer.parseInt(sId)));//??????????????????
        if (i > 0) return "success";
        return "error";

    }

    //??????
    public List<Subject> findAllUserByPageF(int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Subject> lists = subjectMapper.findAll();
        return lists;
    }

    //??????
    public PageInfo<Subject> findAllUserByPageS(int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Subject> lists = subjectMapper.findAll();
        PageInfo<Subject> pageInfo = new PageInfo<Subject>(lists);
        return pageInfo;

    }

    public JSON seachSubject(Subject seach, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Subject> list = subjectMapper.seachSubject(seach);
        PageInfo<Subject> pageInfo = new PageInfo<Subject>(list);

        JSONObject json = new JSONObject();
        json.put("data", formatSubjectList(pageInfo.getList()));
        json.put("total", pageInfo.getTotal());
        return json;
    }

    public List<Subject> formatSubjectList(List<Subject> all){//??????????????????????????????
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getsType().equals("2")) {//?????????????????????
                all.get(i).setAnswer(all.get(i).getAnswer().equals("A") ? "???" : "???");
            }
            all.get(i).setsCar(getCarString(all.get(i).getChapter().getChapterCar()));
            all.get(i).setsType(getSTypeString(all.get(i).getsType()));
            all.get(i).setsKm(geKmString(all.get(i).getsKm()));

        }
        return all;
    }


    @Cacheable(sync = true, value = "subjects",key = "'km'+#subject.getsKm()+'car'+#subject.getsCar()")
    public List<Subject> findSubjectByCarAndKm(Subject subject){
        List<Subject> list = subjectMapper.findSubjectByCarAndKm(subject);

        return list;
    }

    //??????????????????????????????
    public List<Subject> findSubjectByAllId(String[] array){
        List<Subject> subjectByAllId = subjectMapper.findSubjectByAllId(array);
        return subjectByAllId;
    }


    //<!--??????????????????,?????????????????????-->
    public int findSubjectTotalByCarAndKm(String sCar,  String sKm){
        return subjectMapper.findSubjectTotalByCarAndKm(sCar,sKm);
    }

    //excel????????????
    public JSON addSubjectByExcel(MultipartFile file){
        JSONObject json=new JSONObject();//?????????????????????


        final ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);

       // importParams.setSaveUrl(System.getProperty("user.dir")+"\\uploadFile\\");
        //???????????????????????????
        importParams.setStartRows(1);
        try {
            List<Subject> subjects = ExcelImportUtil.importExcel(
                    file.getInputStream(),
                    Subject.class,
                    importParams
            );


            //??????????????????????????????  (?????????????????????)
            subjects = subjects.stream().filter(s -> s.getsQuestion() != null && s.getsQuestion().trim().length() > 0).collect(Collectors.toList());

            //????????????????????????????????????????????????
            List<Chapter> Chapters = chapterMapper.findAllOnly();

            //??????????????????????????????????????????????????????
            List<KnowPoints> knowPoints = knowPointsMapper.findAll();



            /**
             * ????????????????????????????????????:{uploadFile\pic29704081760.JPG}
             * ?????????????????????????????????????????????????????????
             */
            int index=2;
            for (Subject subject : subjects) {

                index++;


                //???????????????????????????????????????

                //??????????????????
                if(!(subject.getsType().equals("1") || subject.getsType().equals("2") || subject.getsType().equals("3"))){
                    json.put("msg","???"+index+"?????????!??????????????????");
                    return json;
                }

                //????????????
                if(!(subject.getsKm().equals("1") || subject.getsKm().equals("4"))){
                    json.put("msg","???"+index+"?????????!????????????");
                    return json;
                }

                //????????????????????????????????????
                if (subject.getsType().equals("3") && (!subject.getsKm().equals("4"))){
                    json.put("msg","???"+index+"?????????!???????????????????????????");
                    return json;
                }


                //???????????????????????????
                List<KnowPoints> know = knowPoints.stream().filter(k -> k.getKnowId().equals(subject.getKnowId())).collect(Collectors.toList());
                if(know.size()==0){
                    json.put("msg","???"+index+"?????????!?????????????????????!");
                    return json;
                }


                //????????????????????????
                List<Chapter> collect = Chapters.stream().filter(c -> c.getKm().equals(subject.getsKm()) && c.getChapterId().equals(subject.getChapterId())).collect(Collectors.toList());
                if(collect.size()==0){
                    json.put("msg","???"+index+"?????????!???????????????????????????");
                    return json;
                }


                String s = subject.getsImg();
                if (s!=null){
                    String pullNmae=s.split("\\\\")[1];//??????pic29704081760.JPG

                    s="/uploadFile/"+pullNmae;//??????????????????

                    //???????????????img??????
                    subject.setsImg(s);
                }else {
                    subject.setsImg("");
                }
            }

            //?????????????????????
            int insert = subjectMapper.insertByExcele(subjects);

            if (insert>0){
                json.put("res",insert);
                json.put("msg","success");
                List<Subject> finalSubjects = subjects;
                new Thread(() -> {
                    for (Subject subject : finalSubjects) {
                        subjectSevice.asyncUpdateSubjectCache(subject);//??????????????????
                    }
                }).start();
            }


        }catch (Exception e){
            e.printStackTrace();
            json.put("msg","????????????,?????????Excel?????????????????????!!");
        }

        return json;
    }


    public String updateExcelTemplate(){
        ImportParams params = new ImportParams();

        //List<Subject> list = ExcelImportUtil.importExcel(
        //        new File(System.getProperty("user.dir")+"\\uploadFile\\excel\\addSubjectExcelTemplate.xlsx"),
        //        Subject.class, params);

        try {
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\uploadFile\\excel\\addSubjectExcelTemplate.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            List<Chapter> chapters = chapterMapper.findAllOnly();
            List<KnowPoints> knowPoints = knowPointsMapper.findAll();

            List<String> knowPointsStringList=new ArrayList<>();
            List<String> chapterStringList=new ArrayList<>();



            for (Chapter chapter : chapters) {

                chapterStringList.add(chapter.getChapterId().toString()+"-"+chapter.getChapterName()+"("+chapter.getChapterCar()+")"+"("+(chapter.getKm().equals("1")?"?????????":"?????????")+")");
            }

            for (KnowPoints knowPoint : knowPoints) {
                knowPointsStringList.add(knowPoint.getKnowId()+"-"+knowPoint.getKnowName());
            }


            XSSFSheet sheet= workbook.getSheetAt(0);//???????????????sheet

            for (int i=1;i<chapterStringList.size()+1;i++){
                XSSFRow row;
                if(sheet.getRow(i)==null){
                    row=sheet.createRow(i);
                }else {
                    row=sheet.getRow(i);
                }
                XSSFCell cell = row.createCell(17);
                cell.setCellValue(chapterStringList.get(i-1));
            }

            for (int i=1;i<knowPointsStringList.size()+1;i++){
                XSSFRow row;
                if(sheet.getRow(i)==null){
                    row=sheet.createRow(i);
                }else {
                    row=sheet.getRow(i);
                }

                XSSFCell cell = row.createCell(16);
                cell.setCellValue(knowPointsStringList.get(i-1));

            }



            FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "\\uploadFile\\excel\\addSubjectExcel.xlsx");
            workbook.write(fileOutputStream);
            fileInputStream.close();
            fileOutputStream.close();



        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();

        }


        return "success";
    }




}
