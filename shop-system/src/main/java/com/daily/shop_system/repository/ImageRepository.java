package com.daily.shop_system.repository;

import com.daily.shop_system.model.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {



}
