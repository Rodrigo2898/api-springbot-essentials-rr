package academy.devdojo.springbootessentialsrr.service.exceptions;

public class ResourceNotFoundExceptionProfissao extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundExceptionProfissao(Object profissao) {
        super("Resource not found profissao: " + profissao);
    }
}
