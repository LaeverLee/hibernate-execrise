package web.emp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.List;
import lombok.Data;

@Data
@Entity
public class Dept {
	@Id
	private Integer deptno;
    private String dname;
    private String loc;
    
    
//    @OneToMany     // 一對多(1)
//    @JoinColumn(name = "DEPTNO"  // 關聯對方的欄位(SQL)
//    ,referencedColumnName = "DEPTNO") // 自己關聯的欄位(SQL)
//    private List<Emp> emps;
   
    
    
    @OneToMany(		// (3)
    		mappedBy = "dept")     // 在對方實體類別內，關聯自方的屬性名
    private List<Emp> emps;
}
