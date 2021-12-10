package com.ers.repository;

import com.ers.model.ErsUsers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<ErsUsers,Long> {

    ErsUsers findByUserEmail(String userEmail);
}
