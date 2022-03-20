package mapper;

import org.modelmapper.ModelMapper;

public class UserActionMapper implements MapperGetter {
    @Override
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
