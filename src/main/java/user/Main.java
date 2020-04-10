package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main
{


    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:user");

        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
           UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

           User user= User.builder()
                   .username("007")
                   .password("spy")
                   .name("James Bond")
                   .email("kem@kedek.hu")
                   .gender(User.Gender.MALE)
                   .dob(LocalDate.parse("1978-05-06"))
                   .enabled(true)
                   .build();
            dao.insert(user);
            User user1 = User.builder()
                    .username("Konyhásnéni")
                    .password("fozes")
                    .name("Konyhás Kati")
                    .email("foz@elek.hu")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1987-12-30"))
                    .enabled(false)
                    .build();
            dao.insert(user1);

            dao.list().stream().forEach(System.out::println);
            System.out.println("getUserbyName:");
            dao.findByUsername("007").stream().forEach(System.out::println);
            System.out.println("getUserbyID:");
            dao.findById(2).stream().forEach(System.out::println);
            System.out.println("deleteUser:");
            dao.deleteUser(user);
            dao.list().stream().forEach(System.out::println);

        }
    }
}
