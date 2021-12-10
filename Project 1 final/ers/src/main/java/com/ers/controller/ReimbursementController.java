package com.ers.controller;
import com.ers.dto.Response;
import com.ers.model.UserReimbursement;
import com.ers.service.ReimbursementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class ReimbursementController {
    static final Logger logger = LogManager.getLogger(ReimbursementController.class.getName());
    private static final String REIMBURSEMENT_ADDED_SUCCESSFULLY = "Reimbursement added successfully";
    @Autowired
    private ReimbursementService service;
    @PostMapping(value = "/user/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> addReimbursement(@RequestParam("reimbAmount") BigDecimal reimbAmount,
                                                     @RequestParam("reimbType") String reimbType,
                                                     @RequestParam("reimbDescription") String reimbDescription,
                                                     @RequestParam("reimbReceipt")List<MultipartFile> reimbReceipt){
        logger.info("reimbursement add start");
        Response response = new Response();
        try{
            service.reimbursementAdd(reimbAmount,
                    reimbType,
                    reimbDescription,
                    reimbReceipt );
            response.setMessage(REIMBURSEMENT_ADDED_SUCCESSFULLY);
            logger.info("reimbursement add success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            logger.error("reimbursement add fail");
            return  new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
        }

    }
    //Get for user

    @GetMapping("/user/reimbursement/request")
    public ResponseEntity<Response> getUserReimbursements(@RequestParam("userId") Long userId){
        logger.info("reimbursement get by user start");
        Response response = new Response();
        List<UserReimbursement> userReimbursementList =  service.findReimbursementById(userId);
        response.setReimbursement(userReimbursementList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/all/reimbursement")
    public ResponseEntity<?> findAllReimbursement(){
        logger.info("reimbursement get admin start");
       return ResponseEntity.ok(service.findAllReimbursement());
    }

    @GetMapping("/admin/approve")
    public ResponseEntity<?> reimbApprove(@RequestParam("userId") Long userId){
        logger.info("reimbursement approve start");
        return ResponseEntity.ok(service.approveReimb(userId));
    }

    @GetMapping("/admin/deny")
    public ResponseEntity<?> reimbDeny(@RequestParam("userId") Long userId){
        logger.info("reimbursement deny start");
        return ResponseEntity.ok(service.denyReimb(userId));
    }

    @GetMapping("/admin/filter/status")
    public ResponseEntity<?> filterByStatus(@RequestParam("status") String status){
        logger.info("reimbursement admin filter by status start");
      return ResponseEntity.ok(service.filterByStatus(status));
    }

    @GetMapping("/user/filter/status")
    public ResponseEntity<?> filterByStatusUser(@RequestParam("status") String status){
        logger.info("reimbursement user filter by status start");
        return ResponseEntity.ok(service.filterByStatus(status));
    }

    @GetMapping("/admin/file/download")
    public ResponseEntity<Resource> downloadFileAdmin(@RequestParam("fileName") String fileName) throws IOException {
        logger.info("reimbursement file download start");
      return service.fileDownloadByAdmin(fileName);
    }
}
