package com.example.wantedpreonboardingbackend.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company getCompany(Long companyId) {
        return companyRepository.findById(companyId).get();
    }
}
