package de.fhro.inf.prg3.a12.icndb.suppliers;

import de.fhro.inf.prg3.a12.icndb.ICNDBApi;
import de.fhro.inf.prg3.a12.icndb.ICNDBService;
import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author Peter Kurfer
 */

public final class RandomJokeSupplier implements Supplier<ResponseWrapper<JokeDto>>
{

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;

    public RandomJokeSupplier()
    {
        icndbApi = ICNDBService.getInstance();
    }

    public ResponseWrapper<JokeDto> get()
    {
        try
        {
            return icndbApi.getRandomJoke().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
