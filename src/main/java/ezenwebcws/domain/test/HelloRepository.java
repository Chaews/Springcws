package ezenwebcws.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<HelloEntity, Long> {}

//Repository <-------> DAO 역할
