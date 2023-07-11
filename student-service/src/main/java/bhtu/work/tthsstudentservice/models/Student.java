package bhtu.work.tthsstudentservice.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Student {
	public static final String[] localSchool = {"Tiểu Học Tây Sơn", "Tiểu Học Thanh Lương", "THCS Đoàn Kết", "THCS Hà Huy Tập", "THPT Thăng Long", "THPT Trần Nhân Tông"}; 
    
    
    @Id
    private  String id;
	private String name;
	private LocalDate dateOfBirth;
	private String school;
	private String householdNumber;
	private String parent;
	private List<EventOfStudent> events = new ArrayList<>();

	public Student(String id, String name, LocalDate dateOfBirth, String school, String householdNumber, String parent) {
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.school = school;
		this.householdNumber = householdNumber;
		this.parent = parent;
	}


	
}
