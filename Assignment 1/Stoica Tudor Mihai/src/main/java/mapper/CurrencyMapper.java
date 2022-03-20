package mapper;

import org.modelmapper.ModelMapper;

public class CurrencyMapper implements MapperGetter {

    @Override
    public ModelMapper getMapper() {

        return new ModelMapper();
    }
}
