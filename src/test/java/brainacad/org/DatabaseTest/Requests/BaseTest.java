package brainacad.org.DatabaseTest.Requests;

import brainacad.org.Database.DatabaseUtil;
import brainacad.org.Database.QueryExecutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest
{
    @BeforeAll
    void setUp()
    {
        DatabaseUtil.resetDatabase();
        DatabaseUtil.initDatabase("Cafe_Test"); // ініціалізація
    }

    @AfterAll
    void teardown()
    {
        DatabaseUtil.resetDatabase();
    }
}