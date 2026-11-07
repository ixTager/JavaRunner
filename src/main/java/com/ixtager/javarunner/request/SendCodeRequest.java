package com.ixtager.javarunner.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeRequest {
    private String code;
}
