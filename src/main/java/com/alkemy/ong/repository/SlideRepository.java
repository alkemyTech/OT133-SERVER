package com.alkemy.ong.repository;

import java.util.List;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.entity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide, String> {
  List<Slide> findAllByOrganization(Organization id);

  @Query("SELECT x.imageUrl, x.orderNumber FROM Slide x")
  public List<Slide> findAllDefined();
}
