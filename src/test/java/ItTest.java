import entities.Category;
import entities.Subject;
import entities.Topic;
import org.junit.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ItTest {
    private EntityManager em;
    private EntityTransaction transaction;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        this.em = emf.createEntityManager();
        this.transaction = em.getTransaction();

    }

    @Test
    public void createTest() throws Exception {
        /*transaction.begin();

        Topic topic1 = new Topic(1L, "topic1");
        Topic topic2 = new Topic(2L, "topic2");
        Topic topic3 = new Topic(3L, "topic3");
        Topic topic4 = new Topic(4L, "topic4");

        Subject subject1 = new Subject(1L, "subject1");
        Subject subject2 = new Subject(2L, "subject2");
        Subject subject3 = new Subject(3L, "subject3");
        Subject subject4 = new Subject(4L, "subject4");

        subject1.setTopics(Arrays.asList(topic1,topic2));

        Category category = new Category(1L, "category1");
        category.setSubjects(Arrays.asList(subject1, subject2));




        em.merge(topic1);
        em.merge(topic2);
        em.merge(subject1);
        em.merge(subject2);
        em.merge(category);
        transaction.commit();*/
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);
        cq.select(root);
        Join subjects = (Join) root.fetch("subjects");
        Join topics = subjects.join("topics");
        Predicate predicate1 = cb.equal(topics.get("name"), "topic2");
        cq.where(predicate1);

        List<Category> resultList = this.em.createQuery(cq).getResultList();
        resultList.stream().flatMap(category -> category.getSubjects().stream()).flatMap(subject -> subject.getTopics().stream()).forEach(System.out::println);
        //System.out.println(resultList);
    }
}