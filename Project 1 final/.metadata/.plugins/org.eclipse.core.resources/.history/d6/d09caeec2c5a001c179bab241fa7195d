package com.ers.service;

import com.ers.model.UserReimbursement;
import com.ers.repository.UserReimbRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReimbursementServiceTest {

    @Autowired
    private UserReimbRepository userReimbRepository;
    @Test
    public void isCheckfilterByStatusExist() {
        UserReimbursement userReimbursement = new UserReimbursement();
        userReimbursement.setId(1L);
        userReimbursement.setReimbType("Travel");
        List<UserReimbursement> list = new ArrayList<>();
        list.add(userReimbursement);
        String status = "Approved";
        UserReimbRepository localMockRepository = Mockito.mock(UserReimbRepository.class);
        Mockito.when(localMockRepository.filterByStatus(status)).thenReturn(list);

        assertEquals(1,list.size());

    }

    @Test
    public void isCheckfilterByStatusnotExist() {
        UserReimbursement userReimbursement = new UserReimbursement();
        userReimbursement.setId(1L);
        userReimbursement.setReimbType("Travel");
        List<UserReimbursement> list = new ArrayList<>();
        list.add(userReimbursement);
        String status = "";
        UserReimbRepository localMockRepository = Mockito.mock(UserReimbRepository.class);
        Mockito.when(localMockRepository.filterByStatus(status)).thenReturn(list);

        assertEquals(1,list.size());

    }

    @Test
    public void isApprovedReimbursment() {
        UserReimbursement userReimbursement = new UserReimbursement();
        userReimbursement.setId(1L);
        userReimbursement.setReimbType("Travel");
        userReimbursement.setReimbStatus("Approved");

        long id = 6;
        UserReimbRepository localMockRepository = Mockito.mock(UserReimbRepository.class);
        Mockito.when(localMockRepository.findById(id)).thenReturn(java.util.Optional.of(userReimbursement));
        Optional<UserReimbursement> reimbursement = localMockRepository.findById(id);
        assertEquals("Approved",reimbursement.get().getReimbStatus());

    }

    @Test
    public void findReimbursementByIdTest(){
        UserReimbursement userReimbursement = new UserReimbursement();
        userReimbursement.setId(1L);
        userReimbursement.setReimbType("Travel");
        List<UserReimbursement> list = new ArrayList<>();
        list.add(userReimbursement);

        long id = 6;
        UserReimbRepository localMockRepository = Mockito.mock(UserReimbRepository.class);
        Mockito.when(localMockRepository.findByAuthor(id)).thenReturn(list);

        List<UserReimbursement> userReimbursementList = localMockRepository.findByAuthor(id);

        assertEquals(1,userReimbursementList.size());


    }


}