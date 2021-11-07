package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersRes {
    private String companyName;
    private String offerDate;
    private String offerStatus;
}
