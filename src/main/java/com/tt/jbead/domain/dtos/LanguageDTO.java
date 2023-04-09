package com.tt.jbead.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDTO {

    private Integer id;

    private String name;

    private String code;

    private List<UserDTO> personList;
}
