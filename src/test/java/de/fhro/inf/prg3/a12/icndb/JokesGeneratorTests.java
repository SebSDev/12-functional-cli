package de.fhro.inf.prg3.a12.icndb;

import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Peter Kurfer
 * Created on 12/28/17.
 */
class JokesGeneratorTests
{

    private JokeGenerator jokeGenerator = new JokeGenerator();

    @Test
    void testRandomStream()
    {
        List<String> list = new LinkedList<>();

        Stream<ResponseWrapper<JokeDto>> stream = jokeGenerator.randomJokesStream();
        stream.limit(10).forEach(jk -> {
            list.add(jk.getValue().toString());
            System.out.println(jk.getValue().toString());
        });

        assertEquals(10, list.size());
    }


    @Test
    void testJokesStream()
    {
        List<String> list = new LinkedList<>();

        Stream<ResponseWrapper<JokeDto>> stream = jokeGenerator.jokesStream();
        stream.limit(700).forEach(jk -> {
            list.add(jk.getValue().toString());
            System.out.println(jk.getValue().toString());
        });

        assertEquals(700, list.size());
    }

}
