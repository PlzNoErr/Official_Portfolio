package com.bodyfit.restapi.model.dto;

import com.bodyfit.restapi.util.PageNavigation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ListAndNavigation<T> {
    private List<T> list;
    private PageNavigation navigation;
}
