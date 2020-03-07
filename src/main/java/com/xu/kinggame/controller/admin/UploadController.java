package com.xu.kinggame.controller.admin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xu.kinggame.config.Constants;
import com.xu.kinggame.util.MyBlogUtils;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
@RequestMapping("/admin")
public class UploadController {

	@PostMapping("/upload/file")
	@ResponseBody
	public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws URISyntaxException{
		if (file.isEmpty()) {
            return ResultGenerator.genFailResult("请选择文件");
        }
		String filename = file.getOriginalFilename();
		String suffixName = filename.substring(filename.lastIndexOf("."));
		//生成文件名通用方法
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Random r = new Random();
		StringBuilder tempName = new StringBuilder();
		tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
		String newFileName = tempName.toString();
		/*try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
		File fileDirectory = new File(Constants.FILE_UPLOAD_PATH);
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_PATH + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        Result result = ResultGenerator.genSuccessResult();
        /*System.out.println("bbbb");*/
        result.setData(MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName);
        /*System.out.println(result.getData());*/
        return result;
	}
	
	/*public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result result = ResultGenerator.genSuccessResult();
        result.setData(MyBlogUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
        return result;
    }*/
	
}
