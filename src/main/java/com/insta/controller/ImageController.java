package com.insta.controller;

import com.insta.exceptionss.Code;
import com.insta.exceptionss.ExceptionResponseDto;
import com.insta.exceptionss.PrivateException;
import com.insta.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;



    // 게시글 작성
    @PostMapping("/image")
    public ExceptionResponseDto uploadPost(@RequestParam(value = "files",required = false) List<MultipartFile> files) {

        if (files == null) {
            throw new PrivateException(Code.WRONG_INPUT_CONTENT);
        }
        List<String> imgPaths = imageService.upload(files);
        System.out.println("IMG 경로들 : " + imgPaths);
        return new ExceptionResponseDto(Code.OK);

    }
}
