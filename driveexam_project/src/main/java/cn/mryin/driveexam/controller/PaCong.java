package cn.mryin.driveexam.controller;

import cn.mryin.driveexam.annotation.UserLoginToken;
import cn.mryin.driveexam.mapper.ChapterMapper;
import cn.mryin.driveexam.mapper.IcnoMapper;
import cn.mryin.driveexam.mapper.IcnoTypeMapper;
import cn.mryin.driveexam.mapper.SubjectMapper;
import cn.mryin.driveexam.pojo.Chapter;
import cn.mryin.driveexam.pojo.Icno;
import cn.mryin.driveexam.pojo.IcnoType;
import cn.mryin.driveexam.pojo.Subject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author MrYin
 * @date 2021/12/18
 */
@Controller
@CrossOrigin
@UserLoginToken
public class PaCong {

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    IcnoTypeMapper icnoTypeMapper;

    @Autowired
    IcnoMapper icnoMapper;


    /**
     * 爬取元贝驾考的题目
     * @param url 爬取的网址
     * @param startId 开始的id
     * @param endId 结束的id
     * @param path 图片保存路径
     * @param imgName 图片前缀名
     * @param endImgName 图片后缀名
     * @param kmId 爬取的科目
     * @return
     * @throws Exception
     */
    @RequestMapping("/pacong")
    @ResponseBody
    public String pacong(@RequestParam(defaultValue = "https://www.ybjk.com/tiku/02dec.htm") String url,
                         @RequestParam(defaultValue = "1") String startId,
                         @RequestParam(defaultValue = "1122") String endId,
                         @RequestParam(defaultValue = "C:/Users/Lenovo/Desktop/元贝驾考图片/") String path,
                         @RequestParam(defaultValue = "yuanBeiTu") String imgName,
                         @RequestParam(defaultValue = ".gif") String endImgName,
                         @RequestParam(defaultValue = "1") String kmId
                         ) throws Exception {
        Subject.SubjectBuilder subjectBuilder = Subject.builder();

        List<Chapter> chapters = chapterMapper.findAllOnly();


        //1: 科目一 4: 科目四

        String subject = "";

        subject = "1";

        int i = Integer.parseInt(startId);


        while (!url.equals("")) {

        if (i==Integer.parseInt(endId))break;
        Document document = Jsoup.connect(url).timeout(4000).userAgent("Mozilla").get();
        String num = "";
        String type = "";
        String chapter = "";
        String question = "";
        String option = "";
        String answer = "";
        String analysis = "";
        String picpath = "";
        String picurl = "";
        String itemA = "";
        String itemB = "";
        String itemC = "";
        String itemD = "";
        //序号
        num = new Integer(i++).toString();
        //章节

        chapter = document.select(".h_Nav a").get(3).text();
       // chapter = "摩托车专用试题";
        //获得数据库对应章节ID
        for (Chapter c : chapters) {
            if (c.getChapterName().equals(chapter)) {
                chapter = c.getChapterId().toString();
                break;
            }
        }

        //题目
        System.out.println(chapter);
        question = document.select("#WinContent div strong a").get(0).text();
        //正确答案
        answer = document.select("#WinContent div ul li i u").get(0).text();
        //分析本题类型
        if (answer.equals("对") || answer.equals("错")) {
            //判断题 0代表科目一
            type = "2";
            answer=answer.equals("对")?"A":"B";
        } else if (answer.length() == 1) {
            //单选题
            type = "1";
        } else {
            //多选题
            type = "3";
        }
        //选择题选项
        if (type.equals("1") || type.equals("3")) {
            //选项option
            Elements elements = document.select("#WinContent div ul li");
            //int f = 0;
            //去除前面的选项如(A、)
            itemA=elements.get(0).text().substring(2,elements.get(0).text().length());
            itemB=elements.get(1).text().substring(2,elements.get(1).text().length());
            itemC=elements.get(2).text().substring(2,elements.get(2).text().length());
            itemD=elements.get(3).text().substring(2,elements.get(3).text().length());
            for (Element element : elements) {
                option += element.text() + ":";
            }
        } else {
            //判断题
            option = "对:错";
        }
        //题目解析
        analysis = document.select("#NoteContent p").get(1).text();
        //图片


        try {
            Element picElement = document.select("#WinContent div div img.min").get(0);
            if (picElement != null) {
                picurl = "https:" + picElement.attr("src");
                picurl = writePicTo(path, imgName+num + endImgName, picurl);//保存图片,返回数据库存储路径
            } else {
                picpath = "";
                picurl = "";
            }
        } catch (Exception e) {
            System.out.println("没有图片");
        }



        System.out.println("题号: " + num);
        System.out.println("类型: " + type);
        System.out.println("A:"+itemA);
        System.out.println("B:"+itemB);
        System.out.println("C:"+itemC);
        System.out.println("D:"+itemD);
        System.out.println("章节: " + chapter);
        System.out.println("题目: " + question);
        System.out.println("选项: " + option);
        System.out.println("答案: " + answer);
        System.out.println("题解: " + analysis);
        System.out.println("图片: " + picurl);
        System.out.println("图片本地地址: " + picpath);

        Subject subjectItem = subjectBuilder.
                sKm(kmId).
                sQuestion(question.trim()).
                chapterId(Integer.parseInt(chapter.trim())).
                itemA(itemA.trim()).
                itemB(itemB.trim()).
                itemC(itemC.trim()).
                itemC(itemC.trim()).
                itemD(itemD.trim()).
                answer(answer.trim()).
                sExplain(analysis).
                sImg(picurl.trim()).
                knowId(1).
                sId(Integer.parseInt(kmId+num.trim().toString())).
                sType(type.trim()).build();


        try {
            //List<Subject> subjects = subjectMapper.selectLikeQuestion(question.trim());
            int insert = subjectMapper.insert(subjectItem);

        }catch (Exception e){
            System.out.println("题号重复");
        }

        //下一题url
        url = "https://www.ybjk.com" + document.select("#WinContent div table tr td a").last().attr("href");
        System.out.println("url: " + url);
    }
       // }

        return "爬取完毕!";
    }




    /**
     * 动态爬取图标
     * @param path  图标保存路径
     * @param startIndex 数据库存储的起始id
     * @param startUrl 爬取的url地址前缀
     * @param urlIndex url拼接地址的数字
     * @param endUrl 爬取的url地址后缀
     * @param urlPathEndId 爬取的最大数值
     * @param urlIndex 爬取的url有效数字部分的起始
     * @param imgStartName 图片保存前缀名
     * @param imgEndName 图片保存后缀名
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/pacongIcno")
    @ResponseBody
    public String pacongGetIcno(
            @RequestParam(defaultValue = "C:/Users/Lenovo/Desktop/元贝驾考图片/") String path,
            @RequestParam(defaultValue = "1001")String startIndex,
            @RequestParam(defaultValue = "356") String urlPathEndId,
            @RequestParam(defaultValue = "http://www.jsyst.cn/jtbz/w") String startUrl,
            @RequestParam(defaultValue = "/") String endUrl,
            @RequestParam(defaultValue = "1") String urlIndex,
            @RequestParam(defaultValue = "icnoimg") String imgStartName,
            @RequestParam(defaultValue = ".jpg") String imgEndName
    ) throws Exception {

        List<IcnoType> icnoTypeS = icnoTypeMapper.findAll();
        int index= Integer.parseInt(startIndex);
        for (int i = Integer.parseInt(urlIndex); i < Integer.parseInt(urlPathEndId)+1; i++) {


            String url=startUrl+i+endUrl;
            System.out.println(url);
            Document document = Jsoup.connect(url).timeout(4000).userAgent("Mozilla").get();
            String icnoTypeName= document.select("#side .seat a").get(2).text().trim();
            String icnoName=document.select("#side .seat a").get(3).text().trim();
            String icnoText="";
            Elements elements = document.select("#side .content .content_left .centent_centent div");
            String imgUrl=elements.get(0).select("img").attr("src");
            IcnoType icnoType = icnoTypeS.stream().filter(t -> t.getIcnoTypeName().equals(icnoTypeName)).collect(Collectors.toList()).get(0);

            if(i<120) {
                String hanyi = elements.get(1).select("p").get(3).text();
                String sezhi = elements.get(1).select("p").get(5).text();
                System.out.println(icnoType);
                icnoText += hanyi + sezhi;
            }


            imgUrl = this.writePicTo(path, imgStartName + (index+i)+imgEndName, imgUrl);
            Icno.IcnoBuilder icnoBuilder = Icno.builder();
            Icno icno = icnoBuilder.icnoName(icnoName).icnoExplain(icnoText).icnoImg(imgUrl).icnoTypeId(icnoType.getIcnoTypeId()).build();

            try {
                int insert = icnoMapper.insert(icno);
            }catch (Exception e){
                System.out.println("插入失败:第"+i);
            }


            System.out.println(icno);
            System.out.println("类型:"+icnoTypeName);
            System.out.println("名称:"+icnoName);
            System.out.println("图片:"+imgUrl);
            System.out.println("解释:"+icnoText);
            index++;
        }
        return null;
    }



    /**
     * 爬取驾英格驾考看题模式试题
     * @param url 爬取的网址
     * @param startId 开始的id
     * @param count 结束的id
     * @param path 图片保存路径
     * @param imgName 图片前缀名
     * @param endImgName 图片后缀名
     * @param KM 爬取的科目
     * @param chapter_id 设置章节id
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/pacongBaoDian4")
    @ResponseBody
    public String pacongBaoDian4(
            @RequestParam(defaultValue ="http://exam.jsyst.cn/d/km4/kt/" ) String url,
            @RequestParam(defaultValue = "41122") String startId,
            @RequestParam(defaultValue = "4") String KM,
            @RequestParam(defaultValue = "1") String count,
            @RequestParam(defaultValue = "15") String chapter_id,
            @RequestParam(defaultValue = "C:/Users/Lenovo/Desktop/元贝驾考图片/") String path,
            @RequestParam(defaultValue = "yuanBeiTu") String imgName,
            @RequestParam(defaultValue = ".gif") String endImgName

    ) throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions(). setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage page = webClient.getPage(url);


        int s_id=Integer.parseInt(startId);
        for (int i = s_id; i < s_id+Integer.parseInt(count); i++) {



            Subject.SubjectBuilder subjectBuilder = Subject.builder();

            String question="";

            String sType="";
            String sKm=KM;
            String itemA="";
            String itemB="";
            String itemC="";
            String itemD="";
            String answer="";
            String sImg="";
            String know_id="1";
            String chapter = chapter_id;
            String analysis = "";


            HtmlElement content = page.getHtmlElementById("content");
            //webClient.waitForBackgroundJavaScript(3000);
            String contentXml = content.asXml();


            Document document = Jsoup.parse(contentXml);

            question = document.select("#id_qus b").text().trim();
            answer=document.select("#id_ans font b").text().trim();
            question=question.substring(question.indexOf(".")+1,question.length()).trim();
            itemA=document.select("#id_ans1").text().trim().substring(2,document.select("#id_ans1").text().trim().length());
            if (itemA.equals("正确")){//是非题判断
                itemA=itemB=itemC=itemD="";
                sType="2";
            }else if(answer.length()>2){
                sType="3";
                itemB=document.select("#id_ans2").text().trim().substring(2,document.select("#id_ans2").text().trim().length());
                itemC=document.select("#id_ans3").text().trim().substring(2,document.select("#id_ans3").text().trim().length());
                itemD=document.select("#id_ans4").text().trim().substring(2,document.select("#id_ans4").text().trim().length());
            }
            else {
                sType="1";
                itemB=document.select("#id_ans2").text().trim().substring(2,document.select("#id_ans2").text().trim().length());
                itemC=document.select("#id_ans3").text().trim().substring(2,document.select("#id_ans3").text().trim().length());
                itemD=document.select("#id_ans4").text().trim().substring(2,document.select("#id_ans4").text().trim().length());
            }

            analysis=document.select("#id_js").text().trim();


            try {
                Element picElement = document.select("#id_bmp img").get(0);
                if (picElement != null) {
                    sImg =  picElement.attr("src");
                    sImg = writePicTo(path, imgName+s_id + endImgName, sImg);
                } else {
                   sImg="";
                }
            } catch (Exception e) {
                System.out.println("没有图片");
            }



            System.out.println("题目:"+question);
            System.out.println("A:"+itemA);
            System.out.println("B:"+itemB);
            System.out.println("C:"+itemC);
            System.out.println("D:"+itemD);
            System.out.println("答案:"+answer);
            System.out.println("解释:"+analysis);
            System.out.println("图片:"+sImg);

            Subject subject = subjectBuilder.sType(sType).sKm(sKm).sQuestion(question).knowId(Integer.parseInt(know_id)).itemA(itemA).itemB(itemB).itemC(itemC).itemD(itemD).answer(answer).sExplain(analysis).sImg(sImg).sId(s_id).chapterId(Integer.parseInt(chapter)).build();


            try{
            int insert = subjectMapper.insert(subject);
            }catch (Exception e){
                System.out.println("重复了==============================");
            }
            System.out.println("成功插入id"+subject.getsId());
            System.out.println("====================================");

            String hrefValue = "javascript:n();";
            ScriptResult s = page.executeJavaScript(hrefValue);//执行js方法
            HtmlPage hpm=(HtmlPage) s.getNewPage();//获得执行后的新page对象
            webClient.waitForBackgroundJavaScript(1000);

            page=hpm;

            s_id++;

        }


        //System.out.println("执行后==============================================================");
        //HtmlElement lpic=hpm.getHtmlElementById("content");
        //
        //
        //Document document1 = Jsoup.parse(lpic.asXml());
        //String text1 = document1.select("#id_qus b").text();
        //System.out.println(text1+"===================================================2");


        webClient.close();
        return null;
    }






    //爬图片 path:输出路径 name:图片名(含后缀名) url:图片地址
    private String writePicTo(String path, String name, String url) {
        URL urlConn = null;
        URLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            urlConn = new URL(url);
            //打开网址连接
            connection = urlConn.openConnection();
            //获取连接中的数据流
            is = connection.getInputStream();
            //设置输出流
            os = new FileOutputStream(new File(path + name));

            //将输入流中的数据输出到输出流中
            byte[] b = new byte[1024];
            int count = 0;
            while((count=is.read(b))!=-1){
                os.write(b,0,count);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/uploadFile/"+name;
    }
}
