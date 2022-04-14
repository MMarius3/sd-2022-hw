package mapper;

import org.modelmapper.ModelMapper;

public class AccountMapper implements MapperGetter {
    @Override
    public ModelMapper getMapper() {

        return new ModelMapper();
    }
}
