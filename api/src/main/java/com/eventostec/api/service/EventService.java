package com.eventostec.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;
        if (data.image() != null){
            imgUrl = this.uploadImg(data.image());
        }
        Event event = new Event();
        event.setTitle(data.title());
        event.setDescription(data.description());
        event.setEventUrl(data.eventUrl());
        event.setDate(new Date(data.date()));
        event.setImgUrl(imgUrl);

        return event;

    }

    private String uploadImg(MultipartFile multipartFile) {
            String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
            try {

                File file = this.convertMultipartFile(multipartFile);
                s3Client.putObject(bucketName, fileName, file);
                file.delete();
                return s3Client.getUrl(bucketName, fileName).toString();

            }catch (Exception e) {
                System.out.println("ERROR UPLOADING IMAGE");
                return null;
            }
    }

    private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
