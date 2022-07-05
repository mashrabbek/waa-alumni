package edu.miu.backend.service;

import edu.miu.backend.dto.JobAdDto;
import edu.miu.backend.entity.JobAd;

import java.util.List;

public interface JobAdService {
    List<JobAd> findAll();

    JobAd findById(int id);


    JobAd save(JobAd student);

    void delete(int id);

    JobAdDto update(JobAdDto jobAdDto, int id) throws Exception;
}
