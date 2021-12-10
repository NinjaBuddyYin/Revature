package com.ers.service;

import com.ers.dto.ManagerReimbResponse;
import com.ers.model.ErsUsers;
import com.ers.model.UserReimbursement;
import com.ers.repository.UserReimbRepository;
import com.ers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ReimbursementService {

    @Autowired
    private UserReimbRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.location}")
    private String directoryPath;


    public void reimbursementAdd(
            BigDecimal reimbAmount, String reimbType, String reimbDescription, List<MultipartFile> reimbReceipt
    ) {
        List<String> fileNames = new ArrayList<>();


        UserReimbursement reimbursement = new UserReimbursement();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date date = new Date();
        Timestamp submitted = new Timestamp(date.getTime());
        reimbursement.setReimbSubmitted(date);
        reimbursement.setReimbAmount(reimbAmount);
        reimbursement.setReimbType(reimbType);
        reimbursement.setReimbDescription(reimbDescription);
        if (!reimbReceipt.isEmpty()) {
            reimbReceipt.forEach(item->{
                String fileName = StringUtils.cleanPath(item.getOriginalFilename());
                Path fileStorage = get(directoryPath, fileName).toAbsolutePath().normalize();
                try {
                    copy(item.getInputStream(), fileStorage , REPLACE_EXISTING);
                    reimbursement.setReimbReceipt(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        reimbursement.setReimbSubmitted(submitted);
        reimbursement.setReimbStatus("Pending");
        ErsUsers users = userRepository.findByUserEmail(user.getUsername());
        reimbursement.setAuthor(users);
        reimbursement.setResolver(users);
        repository.save(reimbursement);


    }

    public List<ManagerReimbResponse> findAllReimbursement() {
        List<ManagerReimbResponse> reimbResponseList = new ArrayList<>();
        List<UserReimbursement> reimbursementList = repository.findAll();
        if(!reimbursementList.isEmpty()){
            reimbursementList.forEach(reimb ->{
                ManagerReimbResponse managerReimbResponse = new ManagerReimbResponse();
                managerReimbResponse.setId(reimb.getId());
                managerReimbResponse.setReimbAmount(reimb.getReimbAmount());
                managerReimbResponse.setReimbReceipt(reimb.getReimbReceipt());
                managerReimbResponse.setReimbStatus(reimb.getReimbStatus());
                managerReimbResponse.setReimbType(reimb.getReimbType());
                managerReimbResponse.setReimbResolved(reimb.getReimbResolved());
                managerReimbResponse.setReimbSubmitted(reimb.getReimbSubmitted());
                managerReimbResponse.setErsUserName(reimb.getAuthor().getErsUserName());
                reimbResponseList.add(managerReimbResponse);
            });
        }

        return reimbResponseList;
    }

    public List<UserReimbursement> findReimbursementById(long userId) {
        List<UserReimbursement> userReimbursementList =  repository.findByAuthor(userId);
        return userReimbursementList;
    }

    public List<ManagerReimbResponse> approveReimb(Long userId){
        Optional<UserReimbursement> userReimbursementOptional = repository.findById(userId);

        if(userReimbursementOptional.isPresent()){
            UserReimbursement  reimbursement = userReimbursementOptional.get();
            reimbursement.setReimbStatus("Approved");
            reimbursement.setReimbResolved(new Date());
            repository.save(reimbursement);
        }

        return findAllReimbursement();
    }

    public List<ManagerReimbResponse> denyReimb(Long userId){
        Optional<UserReimbursement> userReimbursementOptional = repository.findById(userId);

        if(userReimbursementOptional.isPresent()){
            UserReimbursement  reimbursement = userReimbursementOptional.get();
            reimbursement.setReimbStatus("Deny");
            reimbursement.setReimbResolved(new Date());
            repository.save(reimbursement);
        }

        return findAllReimbursement();
    }

    public List<ManagerReimbResponse> filterByStatus(String status){
        List<ManagerReimbResponse> reimbResponseList = new ArrayList<>();
        if(status.isEmpty()){
            return findAllReimbursement();
        }else{
            List<UserReimbursement> reimbursementList = repository.filterByStatus(status.toLowerCase());

            if(!reimbursementList.isEmpty()){
                reimbursementList.forEach(reimb ->{
                    ManagerReimbResponse managerReimbResponse = new ManagerReimbResponse();
                    managerReimbResponse.setId(reimb.getId());
                    managerReimbResponse.setReimbAmount(reimb.getReimbAmount());
                    managerReimbResponse.setReimbReceipt(reimb.getReimbReceipt());
                    managerReimbResponse.setReimbStatus(reimb.getReimbStatus());
                    managerReimbResponse.setReimbType(reimb.getReimbType());
                    managerReimbResponse.setReimbResolved(reimb.getReimbResolved());
                    managerReimbResponse.setReimbSubmitted(reimb.getReimbSubmitted());
                    managerReimbResponse.setErsUserName(reimb.getAuthor().getErsUserName());
                    reimbResponseList.add(managerReimbResponse);
                });
            }
        }
        return reimbResponseList;
    }

    public ResponseEntity<Resource> fileDownloadByAdmin(String fileName) throws IOException {
        Path filePath = get(directoryPath).toAbsolutePath().normalize().resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("File-Name",fileName);
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;File-Name"+resource.getFilename());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .headers(httpHeaders).body(resource);

    }
}
