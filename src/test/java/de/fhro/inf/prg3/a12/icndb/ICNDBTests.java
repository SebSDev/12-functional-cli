package de.fhro.inf.prg3.a12.icndb;

import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Peter Kurfer
 */

public class ICNDBTests
{
    private static final Logger logger = Logger.getLogger(ICNDBTests.class.getName());
    private static final int REQUEST_COUNT = 100;

    private ICNDBApi icndbApi;

    public ICNDBTests()
    {
        this.icndbApi = ICNDBService.getInstance();
    }

    @Test
    void testCollision() throws ExecutionException, InterruptedException
    {
        Set<Integer> jokeNumbers = new HashSet<>();
        int requests = 0;
        boolean collision = false;

        while (requests++ < REQUEST_COUNT)
        {
            CompletableFuture<ResponseWrapper<JokeDto>> jokeCall = icndbApi.getRandomJoke();
            JokeDto joke = jokeCall.get().getValue();

            if (jokeNumbers.contains(joke.getId()))
            {
                logger.info(String.format("Collision at joke %s", joke.getId()));
                collision = true;
                break;
            }

            jokeNumbers.add(joke.getId());
            logger.info(joke.toString());
        }

        assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
    }

    @Test
    void testGetRandomJokeWithChangedName() throws ExecutionException, InterruptedException
    {
        JokeDto j = icndbApi.getRandomJoke("Bruce", "Wayne").get().getValue();
        assertNotNull(j);
        assertFalse(j.getJoke().contains("Chuck"));
        assertFalse(j.getJoke().contains("Norris"));
        logger.info(j.toString());
    }

    @Test
    void testGetJokeById() throws ExecutionException, InterruptedException
    {

        List<Integer> randomIds = new ArrayList<>(10);

        for (int i = 0; i < 10; i++)
        {
            randomIds.add(icndbApi.getRandomJoke().get().getValue().getId());
        }

        for (Integer id : randomIds)
        {
            JokeDto j = icndbApi.getJoke(id).get().getValue();
            assertNotNull(j);
            assertTrue(randomIds.contains(j.getId()));
            logger.info(j.toString());
        }
    }

    @Test
    void testGetJokeCount() throws ExecutionException, InterruptedException
    {
        int jokeCount = icndbApi.getJokeCount().get().getValue();

        assertNotEquals(0, jokeCount);
    }

}
