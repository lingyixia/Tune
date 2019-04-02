package com.feiyu.repository;

import com.feiyu.domain.Music;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PagingAndSortRepository extends PagingAndSortingRepository<Music,Integer>,JpaSpecificationExecutor<Music> {

}

