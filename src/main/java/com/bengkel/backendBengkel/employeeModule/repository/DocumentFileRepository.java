package com.bengkel.backendBengkel.employeeModule.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bengkel.backendBengkel.base.repository.BaseRepository;
import com.bengkel.backendBengkel.employeeModule.model.DocumentFiles;

@Repository
public interface DocumentFileRepository extends BaseRepository<DocumentFiles, UUID> {

    List<DocumentFiles> findByFileName(String fileNames);

    @Modifying
    @Query("DELETE FROM DocumentFiles u where u.fileName = :filename")
    void deleteByFileName(@Param("filename") String fileNames);

}
