package com.eventostec.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repositories.EventRepository;
import com.sun.java.accessibility.util.EventID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CouponService couponService;

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
        event.setRemote(data.remote());

        eventRepository.save(event);

        if (!data.remote()){
            this.addressService.createAddress(data, event);
        }
        return event;
    }

    public List<EventResponseDTO> getUpComingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findUpComingEvents(new Date(),pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity(): "",
                event.getAddress() != null ? event.getAddress().getUf(): "",
                event.isRemote(),
                event.getEventUrl(),
                event.getImgUrl())).stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title,String city,String uf, Date startDate, Date endDate){

        title = (title != null) ? title : "";
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();

        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = this.eventRepository.findFilteredEvents(title,city,uf,startDate,endDate,pageable);

        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(),
                event.getDescription(),event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity(): "",
                event.getAddress() != null ? event.getAddress().getUf(): "",
                event.isRemote(),
                event.getEventUrl(),
                event.getImgUrl())).stream().toList();
    }

    public EventDetailsDTO eventDetails(UUID eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new IllegalArgumentException("Event not found"));
        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()
                )).collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getImgUrl(),
                event.getEventUrl(),
                couponDTOs);
    }


    private String uploadImg(MultipartFile multipartFile) {
            String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
            try {

                File file = this.convertMultipartFile(multipartFile);
                s3Client.putObject(bucketName, fileName, file);
                file.delete();
                return s3Client.getUrl(bucketName, fileName).toString();

            }catch (Exception e) {
                System.out.println("ERROR UPLOADING IMAGE" + e.getMessage());
                return "";
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
