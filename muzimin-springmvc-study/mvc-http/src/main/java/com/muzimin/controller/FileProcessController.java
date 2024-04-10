package com.muzimin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2023-08-07 15:39
 **/
@Controller
public class FileProcessController {

    /**
     * 测试文件的下载功能
     */
    @RequestMapping(value = "/testDown")
    public ResponseEntity<byte[]> fileDown(HttpSession session) throws IOException {
        ServletContext servlet = session.getServletContext();
        String realPath = servlet.getRealPath("/static/img/a.png");
        FileInputStream fis = new FileInputStream(realPath);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-Disposition", "attachment;filename=demo.png");

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        fis.close();
        return responseEntity;
    }

    /**
     * 测试文件的上传功能
     *
     * @param photo
     * @return
     */
    @RequestMapping(value = "/testUp")
    public String fileUp(MultipartFile photo, HttpSession session) throws IOException {
        System.out.println("HTML中定义的名称：" + photo.getName());
        System.out.println("HTML中上传的文件名称：" + photo.getOriginalFilename());

        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("upload_file");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }

        photo.transferTo(new File(realPath + File.separator + photo.getOriginalFilename()));

        return "success";
    }
}
