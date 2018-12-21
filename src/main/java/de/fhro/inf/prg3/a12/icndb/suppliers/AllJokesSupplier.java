package de.fhro.inf.prg3.a12.icndb.suppliers;

import de.fhro.inf.prg3.a12.icndb.ICNDBApi;
import de.fhro.inf.prg3.a12.icndb.ICNDBService;
import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<ResponseWrapper<JokeDto>>
{
    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;
    private int jokeCount;
    private int currentID;
    private int foundJokes;

    public AllJokesSupplier()
    {
        icndbApi = ICNDBService.getInstance();
        try
        {
            jokeCount = icndbApi.getJokeCount().get().getValue();
        }
        catch (Exception e)
        {
            jokeCount = 0;
            e.printStackTrace();
        }
    }

    public ResponseWrapper<JokeDto> get()
    {
        if (foundJokes >= jokeCount)
        {
            currentID = foundJokes = 0;
        }
        currentID++;

        ResponseWrapper<JokeDto> jk;

        try
        {
            jk = icndbApi
                    .getJoke(currentID)
                    .get();
        }
        catch (Exception e)
        {
            return get();
        }

        foundJokes++;
        return jk;
    }

}
