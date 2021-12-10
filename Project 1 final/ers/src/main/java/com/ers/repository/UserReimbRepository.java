package com.ers.repository;

import com.ers.model.UserReimbursement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReimbRepository extends CrudRepository<UserReimbursement,Long> {

    List<UserReimbursement> findAll();

    @Query(value = "select * from ers_reimbursement where reimb_author = ?1 ",
    nativeQuery = true)
    List<UserReimbursement> findByAuthor(long userId);

    @Query(value = "select * from ers_reimbursement where LOWER(reimb_status) = ?1",
    nativeQuery = true)
    List<UserReimbursement> filterByStatus(String status);
}
