package org.ClickOn.service;

import org.ClickOn.dto.AddressDto;
import org.ClickOn.entity.Address;
import org.ClickOn.entity.Users;
import org.ClickOn.repository.AddressRepository;
import org.ClickOn.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final UsersRepository usersRepository;
    private final AddressRepository addressRepository;

    public AddressService(UsersRepository usersRepository, AddressRepository addressRepository) {
        this.usersRepository = usersRepository;
        this.addressRepository = addressRepository;
    }

    // 주소 추가
    @Transactional
    public void addAddress(Long userId, AddressDto dto) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 현재 등록된 주소 목록
        List<Address> currentAddresses = addressRepository.findByUser_Idx(userId);

        if (currentAddresses.size() >= 3) {
            throw new IllegalStateException("주소는 최대 3개까지만 등록할 수 있습니다.");
        }

        // 기본 배송지 설정 시 기존 기본 배송지 해제
        if (dto.isDefault()) {
            currentAddresses.stream()
                    .filter(Address::isDefault)
                    .forEach(addr -> addr.setDefault(false));
            addressRepository.saveAll(currentAddresses);
        }

        Address newAddress = Address.builder()
                .user(user)
                .recipientName(dto.getRecipient())
                .phoneNumber(dto.getPhone())
                .postalCode(dto.getPostalCode())
                .addressLine1(dto.getAddress1())
                .addressLine2(dto.getAddress2())
                .isDefault(dto.isDefault())
                .build();

        user.addAddress(newAddress);
        addressRepository.save(newAddress);
    }

    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        // 해당 유저의 모든 주소 디폴트값 false
        addressRepository.resetAllDefaults(userId);
        // 해당 주소만 기본값 true
        addressRepository.setDefault(addressId);
        addressRepository.flush();
    }
}
