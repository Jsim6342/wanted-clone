package com.example.demo.src.profile.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersRes {
    private Long offerId;
    private Long companyId;
    private String companyName;
    private String offerDate;
    private String offerStatus;
}
