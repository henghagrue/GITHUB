package com.upbchain.springmvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.upbchain.springmvc.model.Person;

@Controller
@RequestMapping("/mvc1")
public class shareMappingController {
    @RequestMapping("/hello")
    public String hello(){        
        return "hello";
    }
    /**
     * 自动匹配参数
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/person")
    public String toPerson(String name,double age){
        System.out.println(name+" "+age);
        return "hello";
    }
    /**
     * 自动装箱(转实体)
     * @param p
     * @return
     */
    @RequestMapping("/person1")
    public String toPerson(Person p){
		System.out.println(this.getClass().getName()+";"+ p.getName()+" "+p.getAge());
        return "hello";
    }
    /**
     * 使用InitBinder来处理Date类型的参数
     * @param date
     * @return
     */
    //the parameter was converted in initBinder
    @RequestMapping("/date")
    public String date(Date date){
        System.out.println(date);
        return "hello";
    }
    //At the time of initialization,convert the type "String" to type "date"
   @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),
                true));
    }
   /**
    * 向前台传递参数
    * @param map
    * @return
    */
   @RequestMapping("/show")
   public String showPerson(Map<String,Object> map){
       Person p =new Person();
       map.put("p", p);
       p.setAge(20);
       p.setName("jayjay");
       return "show";
   }
   /**
    * 给ajax调用
    * @param name
    * @param pw
    */
   //pass the parameters to front-end using ajax
   @RequestMapping("/getPerson")
   public void getPerson(String name,PrintWriter pw){
       pw.write("hello,"+name);        
   }
   
   @RequestMapping("/name")
   public String sayHello(){
       return "name";
   }
   /**
    * 前端使用该代码调用
     $(function(){
              $("#btn").click(function(){
                  $.post("mvc/getPerson",{name:$("#name").val()},function(data){
                      alert(data);
                  });
              });
          });
    */
   
   /**
    * 使用redirect处理跳转请求
    * @return
    */
   //redirect 
   @RequestMapping("/redirect")
   public String redirect(){
       return "redirect:show";
   }
   
   /**
    * 上传文件，spring.xml需添加配置,添加commons-fileupload.jar依赖
    * @param req
    * @return
    * @throws Exception
    */
   @RequestMapping(value="/upload",method=RequestMethod.POST)
   public String upload(HttpServletRequest req) throws Exception{
       MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
       MultipartFile file = mreq.getFile("file");
       String fileName = file.getOriginalFilename();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
       File ofile = new File(req.getSession().getServletContext().getRealPath("/")+"upload/");
       if(!ofile.exists())ofile.mkdirs();
       FileOutputStream fos = new FileOutputStream(ofile.getPath()+"/"+sdf.format(new Date())+fileName.substring(fileName.lastIndexOf('.')));
       fos.write(file.getBytes());
       fos.flush();
       fos.close();
       
       return "hello";
   }
   //打开上传页面
   @RequestMapping("/uploadfile")
   public String uploadfile(){
       return "upload";
   }
   
   /**
    * 使用@RequestParam注解指定参数的name
    * @param id
    * @param name
    * @return
    */
   @RequestMapping(value="/param")
   public String testRequestParam(@RequestParam(value="id") Integer id,
           @RequestParam(value="name")String name){
       System.out.println(id+" "+name);
       return "/hello";
   }    
   
   
   /**
    * restful风格
    * 配置支持PUT，DELETE方式（web.xml）增加配置
    * @param id
    * @return
    */
       
   @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
   public void get(@PathVariable("id") Integer id,PrintWriter pw){
       System.out.println("get"+id);
       pw.write("get"+id);
//       return "/hello";
   }
   
   @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
   public void post(@PathVariable("id") Integer id,PrintWriter pw){
       System.out.println("post"+id);
       pw.write("post"+id);
//       return "/hello";
   }
   
   @RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
   public void put(@PathVariable("id") Integer id,PrintWriter pw){
       System.out.println("put"+id);
       pw.write("put"+id);
   }
   
   @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
   public void delete(@PathVariable("id") Integer id,PrintWriter pw){
       System.out.println("delete"+id);
       pw.write("delete"+id);
   }
   //打开上传页面
   @RequestMapping("/form")
   public String form(){
       return "form";
   }
   
   //打开上传页面
   @RequestMapping("/test")
   public String test(){
//	   int a = 1/0;
//	   javax.el.ELManager rl = null;
       return "test";
   }
   
   
}
