import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import json_conversion.adapters.*;
import symulacja.Symulacja;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        assert args.length == 2;
        String file = args[0];
        String jsonIn = readFileAsString(file);

        StrategiaRozwojuAdapter sRA = new StrategiaRozwojuAdapter();
        StrategiaProdukcjiAdapter sPA = new StrategiaProdukcjiAdapter();
        StrategiaKupowaniaAdapter sKA = new StrategiaKupowaniaAdapter();
        Moshi moshi = new Moshi.Builder()
                .add(new InfoAdapter())
                .add(sRA).add(sPA).add(sKA)
                .add(new RobotnikAdapter())
                .add(new SpekulantAdapter())
                .add(new SymulacjaAdapter())
                .build();
        sRA.setMoshi(moshi);
        sPA.setMoshi(moshi);
        sKA.setMoshi(moshi);
        JsonAdapter<Symulacja> jsonAdapter = moshi.adapter(Symulacja.class);

        Symulacja symulacja = jsonAdapter.fromJson(jsonIn);

        String outJson = symulacja.symulacjaJson();

        PrintWriter out = new PrintWriter(args[1]);
        out.println(outJson);
        out.close();
    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
