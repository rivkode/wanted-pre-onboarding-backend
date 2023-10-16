package com.example.wantedpreonboardingbackend.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // long id 가 예상하는 id 가 아닐경우 예외 처리 하기
    public Company getCompany(Long companyId) {
        return companyRepository.findById(companyId).get();
    }
}