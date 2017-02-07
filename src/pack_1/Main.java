package pack_1;

import h.utils.HUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Constructor;

import enities.*;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by magic_000 on 06/02/2017.
 */
public class Main {

    public static void main(String[] args) {
        //System.out.println("Java work");

        /*Constructor[] c= Singleton_.class.getDeclaredConstructors();


        System.out.println(c.length);


        for (Constructor c_:c) {
            c_.setAccessible(true);

            try {
                Singleton_ s= (Singleton_) c_.newInstance();
                System.out.println(s.name);
                System.out.println("OKOOOKO");


            }catch (Exception e){
                System.out.println(e);
            }


        }*/


        /*EagerInitializedSingleton instanceOne = EagerInitializedSingleton.getInstance();
        EagerInitializedSingleton instanceTwo = null;
        try {
            Constructor[] constructors = EagerInitializedSingleton.class.getDeclaredConstructors();

            System.out.println(constructors.length);

            for (Constructor constructor : constructors) {
                //Below code will destroy the singleton pattern
                constructor.setAccessible(true);
                instanceTwo = (EagerInitializedSingleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(instanceOne.hashCode());
        System.out.println(instanceTwo.hashCode());*/



        /*Test run hibernate*/


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

            String sql = "Select e from " + Employee.class.getName() + " e "
                    + " order by e.empName, e.empNo ";

            // Tạo đối tượng Query.
            Query<Employee> query = session.createQuery(sql);

            // Thực hiện truy vấn.
            List<Employee> employees = query.getResultList();

            for (Employee emp : employees) {
                System.out.println("Emp: " + emp.getEmpNo() + " : "
                        + emp.getEmpName());
            }

            // Commit dữ liệu
            session.getTransaction().commit();


        }catch ( Exception e){

            e.printStackTrace();

            //session.getTransaction().rollback();
        }






    }
}
