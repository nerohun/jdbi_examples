package user;




import com.sun.source.tree.Scope;
import ex8.YearArgumentFactory;
import ex8.YearColumnMapper;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterColumnMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlCall;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.w3c.dom.ls.LSOutput;


import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@RegisterArgumentFactory(YearArgumentFactory.class)
@RegisterColumnMapper(YearColumnMapper.class)
@RegisterBeanMapper(User.class)
public interface UserDao {

    @SqlUpdate("""
            CREATE TABLE users (
                id IDENTITY PRIMARY KEY ,
                username VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                gender VARCHAR NOT NULL,
                dob DATE NOT NULL,
                enabled BOOLEAN NOT NULL

            )
            """
    )
    void createTable();


    //Long insert(User user): az adott felhasználó mentése az adatbázisba, a felhasználó automatikusan generált azonosítóját adja vissza

    @SqlUpdate("INSERT INTO users (username, password, name, email, gender, dob, enabled) VALUES (:username,:password,:name,:email,:gender,:dob, :enabled )")
    @GetGeneratedKeys
    Long insert(@BindBean User user );


    //Optional<User> findById(long id): az adott azonosítójú felhasználó betöltése az adatbázisból
    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> findById(@Bind("id") long id);

    //Optional<User> findByUsername(String username): az adott felhasználói nevű felhasználó betöltése az adatbázisból

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUsername(@Bind("username") String username);

    //void delete(User user): az adott felhasználó törlése az adatbázisból
    @SqlUpdate("Delete FROM users WHERE username = :username")
    void deleteUser(@BindBean User user);

    //List<User> list(): az összes felhasználó betöltése az adatbázisból
    @SqlQuery("SELECT * FROM users ORDER BY id")
    @RegisterBeanMapper(User.class)
    List<User> listUsers();


}
