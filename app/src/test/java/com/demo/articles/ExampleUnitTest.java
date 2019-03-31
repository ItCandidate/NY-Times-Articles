package com.demo.articles;

import com.demo.articles.ui.articles_details.ArticleDetailModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTitle(){
        ArticleDetailModel articleEntity = new ArticleDetailModel();
        articleEntity.setTitle("Candidate");
        assertEquals(articleEntity.getTitle(), "Candidate");
    }
}