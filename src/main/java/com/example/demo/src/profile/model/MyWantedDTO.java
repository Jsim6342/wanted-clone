package com.example.demo.src.profile.model;

import com.example.demo.src.company.model.GetCompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MyWantedDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private User user;
        private List<String> interestList;
        private ApplicationStatus applicationStatus;
        private OfferStatus offerStatus;
        private List<RecommendEmployment> recommendEmploymentList;
        private List<LikeEmployment> likeEmploymentList;
        private List<BookmarkEmployment> bookmarkEmploymentList;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String userEmail;
        private String userPhoneNumber;
        private Long point;

    }

    @Data
    @NoArgsConstructor
    public static class ApplicationStatus {
        private Integer complete = 0;
        private Integer passDocument = 0;
        private Integer finalPass = 0;
        private Integer failed = 0;

        public void addComplete() {
            this.complete +=  1;
        }

        public void addPassDocument() {
            this.passDocument += 1;
        }

        public void addFinalPass() {
            this.finalPass += 1;
        }

        public void addFailed() {
            this.failed += 1;
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OfferStatus {
        private Integer resumeWanted;
        private Integer resumeOpen;
        private Integer offerCount;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendEmployment {
        private Long employmentIdx;
        private String empTitle;
        private String companyName;
        private String companyLocation;
        private Long reward;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeEmployment {
        private Long employmentIdx;
        private String empTitle;
        private String companyName;
        private String companyLocation;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookmarkEmployment {
        private Long employmentIdx;
        private String empTitle;
        private String companyName;
        private String companyLocation;
    }



}
