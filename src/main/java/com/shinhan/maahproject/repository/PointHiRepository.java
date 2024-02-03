package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.PointHiMultikey;
import com.shinhan.maahproject.vo.PointHiVO;

public interface PointHiRepository extends CrudRepository<PointHiVO, PointHiMultikey>{

}
