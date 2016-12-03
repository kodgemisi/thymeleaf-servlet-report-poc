import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by destan on 12/3/16.
 */
public class Dao {

    private static List<Company> companies;

    static {
        Fairy fairy = Fairy.create();
        companies  = IntStream
                .range(0, 10)
                .mapToObj(i -> fairy.company())
                .collect(Collectors.toList());
    }

    public static List<Company> listAllCompanies() {
        return companies;
    }
}
