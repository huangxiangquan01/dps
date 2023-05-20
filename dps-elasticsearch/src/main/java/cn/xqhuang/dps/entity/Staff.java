package cn.xqhuang.dps.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Staff {

	private Byte staffId;

	private String firstName;

	private String lastName;

	private Short addressId;

	private String email;

	private String username;

	private String password;

	private String lastUpdate;

}