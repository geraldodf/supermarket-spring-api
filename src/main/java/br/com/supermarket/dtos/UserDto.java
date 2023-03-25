package br.com.supermarket.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserDto {
    private ArrayList<Long> addressesIds;
    private String name;
    private String surname;
    private String phoneNumber;
    private String phoneNumberReserve;
    private String email;
    private String password;

}
