package com.ixtager.javarunner.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeResponse <T>{
    private T data;
}
