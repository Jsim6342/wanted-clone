package com.example.demo.src.profile.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeekStatus {

    @NotBlank(message = "구직 여부 설정 값이 비어있습니다.")
    private String seekStatus;
}
