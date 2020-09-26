package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

public abstract class TransactionResponse extends GenesisResponse {

	public TransactionResponse(HttpStatus status, String message) {
		super(status, message);
	}
}
