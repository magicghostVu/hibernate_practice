package pack_1;

import enities.Employee;
import h.utils.HUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by magic_000 on 06/02/2017.
 */
public class Main {

    public static void main(String[] args) {



        SessionFactory sf= HUtils.getSessionFactory();
        Session session= sf.getCurrentSession();


        try {
            // Tất cả các lệnh hành động với DB thông qua Hibernate
            // đều phải nằm trong 1 giao dịch (Transaction)
            // Bắt đầu giao dịch
            session.getTransaction().begin();

            // Tạo một câu lệnh HQL query object.
            // Tương đương với Native SQL:
            // Select e.* from EMPLOYEE e order by e.EMP_NAME, e.EMP_NO

            String sql = "select e from " + Employee.class.getName() + " as e "
                    + " order by e.empName, e.empNo ";
            // Tạo đối tượng Query.
            Query<Employee> query = session.createQuery(sql);
            // Thực hiện truy vấn.
            List<Employee> employees = query.getResultList();
            for (Employee emp : employees) {
                System.out.println("Emp: " + emp.getEmpNo() + " : "
                        + emp.getEmpName());
            }
            //Employee e= employees.get(0);


            //System.out.println(e.getEmpId());

            //e.setEmpName("Hồng Phú");

            //session.saveOrUpdate(e);

            // Commit dữ liệu
            session.getTransaction().commit();
            session.close();
        }catch ( Exception e){
            e.printStackTrace();
            //session.getTransaction().rollback();
        }






    }
}
