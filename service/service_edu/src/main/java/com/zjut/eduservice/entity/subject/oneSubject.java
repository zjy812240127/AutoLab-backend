package com.zjut.eduservice.entity.subject;

import com.zjut.eduservice.entity.EduSubject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joya Zou
 * @date 2021年07月02日 13:55
 */
@Data
public class oneSubject {
    private String id;
    private String title;
    private List<twoSubject> children = new ArrayList<>();
}
