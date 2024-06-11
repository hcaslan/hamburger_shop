package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constant.EStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatusUpdateModel {
    private String authId;
    private EStatus status;
}
