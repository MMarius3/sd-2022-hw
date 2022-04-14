package mapper;

import org.modelmapper.ModelMapper;

public class UserMapper implements MapperGetter {
    @Override
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
