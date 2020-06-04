package kz.dilau.htcdatamanager.service.impl;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String organization;
    private String designation;
    private int salary;

}
