package com.feiyu.service;

import com.feiyu.domain.Artist;
import com.feiyu.domain.Music;
import com.feiyu.repository.PagingAndSortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
@Transactional
public class PagingAndSortService {
    @Autowired
    private PagingAndSortRepository pagingAndSortRepository;

    public Page<Music> getPageMusic(int page, int size, final String country, final String sex) {
        Pageable pageable = new PageRequest(page, size);
        if (country.equals("0") && sex.equals("0")) {
            return pagingAndSortRepository.findAll(pageable);
        } else {
            return pagingAndSortRepository.findAll(new Specification<Music>() {
                @Override
                public Predicate toPredicate(Root<Music> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Join<Music, Artist> artistJoin = root.join("artistBean");
                    if (!country.equals("0") && !sex.equals("0")) {
                        Path<String> artistCountry = artistJoin.get("artistCountry");
                        Path<String> artistSex = artistJoin.get("sex");
                        query.where(cb.equal(artistCountry, country + ""), cb.equal(artistSex, sex + ""));
                        return null;
                    }
                    if (!country.equals("0")) {
                        return cb.equal(artistJoin.get("artistCountry"), country + "");
                    } else if (!sex.equals("0")) {
                        return cb.equal(artistJoin.get("sex"), sex + "");
                    }
                    return null;
                }
            }, pageable);
        }
    }

    public Page<Music> getSearchPageMusic(int page, int size, final String searchContent) {
        Pageable pageable = new PageRequest(page, size);
        return pagingAndSortRepository.findAll(new Specification<Music>() {
            @Override
            public Predicate toPredicate(Root<Music> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Music, Artist> artistJoin = root.join("artistBean");
                Path<String> artistName = artistJoin.get("artistName");
                Path<String> musicName = root.get("musicName");
                Path<String> musicAlbum = root.get("album");
                Predicate p1 = cb.like(artistName, "%" + searchContent + "%");
                Predicate p2 = cb.like(musicName, "%" + searchContent + "%");
                Predicate p3 = cb.like(musicAlbum, "%" + searchContent + "%");
                return cb.or(p1, p2, p3);
            }
        }, pageable);
    }

    public Page<Music> getTypePageMusic(int page, int size, final String type) {
        Pageable pageable = new PageRequest(page, size);
        return pagingAndSortRepository.findAll(new Specification<Music>() {
            @Override
            public Predicate toPredicate(Root<Music> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(cb.like(root.get("type"), "%" + type + "%")); //这里可以设置任意条查询条件
                return null;
            }
        }, pageable);
    }

    public Page<Music> getCountryPageMusic(final int country) {
        Sort sort = new Sort(Sort.Direction.DESC, "allNum");
        Pageable pageable = new PageRequest(0, 10, sort);
        return pagingAndSortRepository.findAll(new Specification<Music>() {
            @Override
            public Predicate toPredicate(Root<Music> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Music, Artist> artistJoin = root.join("artistBean");
                if (country == 1) {
                    return cb.equal(artistJoin.get("artistCountry"), 1);
                } else {
                    return cb.notEqual(artistJoin.get("artistCountry"), 1);
                }
            }
        }, pageable);
    }
}