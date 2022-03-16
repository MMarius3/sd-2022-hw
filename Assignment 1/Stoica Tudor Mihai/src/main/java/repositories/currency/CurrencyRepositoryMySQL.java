package repositories.currency;

import repositories.dtos.CurrencyDTO;

public class CurrencyRepositoryMySQL implements CurrencyRepository<CurrencyDTO> {
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(CurrencyDTO object) {
        return 0;
    }

    @Override
    public CurrencyDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(CurrencyDTO object) {

    }
}
