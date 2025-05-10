package co.edu.poli.ces3.socrates.socrates.repositories;

import co.edu.poli.ces3.socrates.socrates.config.MysqlConnection;
import co.edu.poli.ces3.socrates.socrates.interfaces.ICrud;

import java.util.List;
import java.util.Objects;

public class CourseRepository extends MysqlConnection implements ICrud {

    public CourseRepository() throws Exception {
    }

    /**
     *
     */
    @Override
    public void disconnect() {

    }

    /**
     * @return
     */
    @Override
    public int insert() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public List<Objects> select() {
        return List.of();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public int update(int id) {
        return 0;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public double delete(int id) {
        return 0;
    }


}
