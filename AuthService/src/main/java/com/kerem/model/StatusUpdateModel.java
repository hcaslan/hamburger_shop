package com.kerem.model;

import com.kerem.constant.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatusUpdateModel {
    private String authId;
    private EStatus status;
}
