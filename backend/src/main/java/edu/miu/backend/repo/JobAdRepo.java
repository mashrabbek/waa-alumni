package edu.miu.backend.repo;

import edu.miu.backend.dto.responseDto.JobAdResponseDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.JobAd;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAdRepo extends CrudRepository<JobAd, Integer> {


    List<JobAd> findByCreator (User user);

    @Query("select j from JobAd j where j.creator <> ?1")
    List<JobAd> findByCreatorExcluded(User user);
}
