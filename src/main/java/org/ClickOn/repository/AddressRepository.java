package org.ClickOn.repository;

import org.ClickOn.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Idx(Long userIdx);
    // 해당 유저의 모든 주소에서 isDefault - false 로 변경
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.idx = :userId")
    void resetAllDefaults(@Param("userId") Long userId);

    // 기본값으로 지정한 주소만 isDefault = true 처리
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Address a SET a.isDefault = true WHERE a.idx = :addressId")
    void setDefault(@Param("addressId") Long addressId);
}
