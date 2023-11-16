package com.imageUploding.demo.controller;

import com.imageUploding.demo.Repository.UplodeRepository;
import jakarta.servlet.http.HttpSession;
import com.imageUploding.demo.model.images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UplodeRepository UploadRepo;

    @GetMapping("/")
    public String index(Model m)
    {
        List<images> list =UploadRepo.findAll();
        m.addAttribute("list",list);
        return "index";
    }

    @PostMapping("/imageUploader")
    public String imageUploader(@RequestParam MultipartFile img, HttpSession session)
    {
        //System.out.println("hello test");
        //System.out.println(img.getOriginalFilename());
        images im = new images();
        im.setImageName(img.getOriginalFilename());

        images uplodeImg = UploadRepo.save(im);
        if (uplodeImg != null)
        {
            try{
                File saveFile =new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsoluteFile()+File.separator+img.getOriginalFilename());
                Files.copy(img.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

       
        return "redirect:/index";
    }
}
