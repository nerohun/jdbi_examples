package user;

import ex9.LegoSetDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Random;

public class Main
{


    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:user");

        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
           UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

           User user= User.builder()
                   .username("James")
                   .password("spy")
                   .name("007")
                   .email("kem@kedek.hu")
                   .gender(User.Gender.MALE)
                   .dob(LocalDate.parse("1978-05-06"))
                   .enabled(true)
                   .build();
            dao.insert(user);
            User user1 = User.builder()
                    .username("Kati")
                    .password("konyhas")
                    .name("XXL")
                    .email("fouz@kedek.hu")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1987-12-30"))
                    .enabled(false)
                    .build();
            dao.insert(user1);

            dao.listUsers().stream().forEach(System.out::println);
            System.out.println("getUserbyName:");
            dao.findByUsername("James").stream().forEach(System.out::println);
            System.out.println("getUserbyID:");
            dao.findById(2).stream().forEach(System.out::println);
            System.out.println("deleteUser:");
            dao.deleteUser(user);
            dao.listUsers().stream().forEach(System.out::println);

        }
    }
}
