package com.bengkel.backendBengkel.bengkel.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bengkel.backendBengkel.base.repository.BaseRepository;
import com.bengkel.backendBengkel.bengkel.model.Kendaraan;

@Repository
public interface KendaraanRepository extends BaseRepository<Kendaraan, String>, JpaSpecificationExecutor<Kendaraan> {

}
