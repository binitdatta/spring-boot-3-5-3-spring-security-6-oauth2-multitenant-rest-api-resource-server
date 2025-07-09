package com.rollingstone.repository;


import com.rollingstone.model.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    List<OrderHeader> findByCustomerNumber(String customerNumber);

    List<OrderHeader> findByOrderStatus(String orderStatus);

}

