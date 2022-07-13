package edu.miu.backend.repo;

import edu.miu.backend.dto.responseDto.JobAdResponseDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.JobAd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAdRepo extends CrudRepository<JobAd, Integer> {

    List<JobAd> findByCreator (String username);
}
