package mapper;

import org.modelmapper.ModelMapper;

public class ClientMapper implements MapperGetter {

    @Override
    public ModelMapper getMapper() {

        return new ModelMapper();
    }
}
