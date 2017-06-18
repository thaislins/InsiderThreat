package br.imd.exception;

/**
 * Classe que cria uma Exceção caso o usuário não seja encontrado na estrutura
 * durante operação de busca
 */
public class UserNotFoundException extends NullPointerException {

	public UserNotFoundException(String message) {
		super(message);
	}
}
