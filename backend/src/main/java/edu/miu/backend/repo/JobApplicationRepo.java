package edu.miu.backend.repo;

import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepo extends CrudRepository<JobApplication, Integer> {
    Optional<JobApplication> findByJobAdsAndStudent(JobAd jobAd, Student student);

    Optional<List<JobApplication>> findByJobAds(JobAd jobAd);

    @Query("select j from JobApplication ja join ja.jobAds j")
    List<JobAd> findAllJobAds();
}
