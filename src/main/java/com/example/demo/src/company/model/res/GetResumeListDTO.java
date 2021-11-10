package com.example.demo.src.company.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetResumeListDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO {
        private resumeDTO resumeDTO;
        private List<String> skillList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class resumeDTO {
        private Long resumeIdx;
        private String title;
        private String userName;
        private String major;
    }


}
