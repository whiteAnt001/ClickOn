package org.ClickOn.repository;

import org.ClickOn.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {

}
