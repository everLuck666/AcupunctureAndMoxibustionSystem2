package net.seehope.config;

import net.seehope.UserService;
import net.seehope.common.UserType;
import net.seehope.filter.AllowOriginFilter;
import net.seehope.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    UserService userService;

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        AllowOriginFilter myFilter = new AllowOriginFilter();
        filterBean.setFilter(myFilter);
        ArrayList urls = new ArrayList();
        urls.add("/*");
        filterBean.setUrlPatterns(urls);
        Map map = new HashMap();
        map.put("count","100");
        filterBean.setInitParameters(map);
        return filterBean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //拦截用户
        List includePathLists = new ArrayList();
        includePathLists.add("/index/plan");
        includePathLists.add("/index/planRecord");
        includePathLists.add("/index/appendPlan");
        includePathLists.add("/index/ilustrate");
        includePathLists.add("index/symptomInformation");
        includePathLists.add("/ilustrate/record");
        includePathLists.add("/ilustrate/ilustrateAll");
        includePathLists.add("/ilustrate/ilustrateInfomation");
        includePathLists.add("/shopping/**");
        includePathLists.add("/file/video2");
        includePathLists.add("/article/articleAll");
        includePathLists.add("/index/information");
        includePathLists.add("/index/seachSymptom");
        List userExclude = new ArrayList();
        userExclude.add("/user/login");

        registry.addInterceptor(new MyInterceptor(new String[]{UserType.USER.getType()+"",UserType.SUPERMANAGER.getType()+""},"不是用户token",userService))
                .addPathPatterns(includePathLists);
//
//
//        //拦截学生
//        List studentIncludePathLists = new ArrayList();
//        studentIncludePathLists.add("/choose/choose");
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.STUDENT.getType()+""},"不是学生token",userService))
//                .addPathPatterns(studentIncludePathLists);

//管理员拦截器
        List managerIncludePathLists = new ArrayList();
        managerIncludePathLists.add("/article/**");
        managerIncludePathLists.add("/ilustrate/**");
        managerIncludePathLists.add("/user/**");
        managerIncludePathLists.add("/file/**");
        managerIncludePathLists.add("/goods/**");
        managerIncludePathLists.add("/orders/**");
        List managerExclude = new ArrayList();
        managerExclude.add("/ilustrate/record");
        managerExclude.add("/user/login");
        managerExclude.add("/user/manager");
        managerExclude.add("/ilustrate/ilustrateAll");
        managerExclude.add("/ilustrate/record");
        managerExclude.add("/ilustrate/ilustrateInfomation");
        managerExclude.add("/file/video2");
        managerExclude.add("/article/articleAll");



        registry.addInterceptor(new MyInterceptor(new String[]{UserType.SUPERMANAGER.getType()+""},"不是管理员token",userService))
                .addPathPatterns(managerIncludePathLists)
                .excludePathPatterns(managerExclude);
////
////超级管理员拦截器
//        List superManagerIncludePathLists = new ArrayList();
//        superManagerIncludePathLists.add("/admin/Admin/**");
//        superManagerIncludePathLists.add("/admin/AdminInfo");
//        superManagerIncludePathLists.add("/manager/incomeStatistics/**");
//
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.SUPERMANAGER.getType()+""},"不是超级管理token",userService))
//                .addPathPatterns(superManagerIncludePathLists);
//
//        //员工拦截器
//        List workIncludePathLists = new ArrayList();
//        workIncludePathLists.add("/admin/getOrderMsg");
//        workIncludePathLists.add("/admin/verification");
//        workIncludePathLists.add("/admin/getOrderMsg");
//        workIncludePathLists.add("/admin/verification");
//        workIncludePathLists.add("/admin/getRecord");
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.WORK.getType()+""},"不是员工token或者没有权限",userService))
//                .addPathPatterns(workIncludePathLists);



    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        File file = new File("AcupunctureAndMoxibustionSystem-controller");
        registry.addResourceHandler("/static/**").addResourceLocations("file:"+file.getAbsolutePath()+"/src/main/resources/static/");



    }
}
