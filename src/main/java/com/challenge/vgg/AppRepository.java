package com.challenge.vgg;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppRepository extends JpaRepository<AppModel, Long> {
    Optional<AppModel> findByUsername(String username);
}