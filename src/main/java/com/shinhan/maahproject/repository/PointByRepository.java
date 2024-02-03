package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;

public interface PointByRepository extends CrudRepository<PointByVO, PointByMultikey> {

}
