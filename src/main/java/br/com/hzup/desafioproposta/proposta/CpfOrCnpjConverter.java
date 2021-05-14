package br.com.hzup.desafioproposta.proposta;

import javax.persistence.AttributeConverter;

import org.springframework.security.crypto.encrypt.Encryptors;

public class CpfOrCnpjConverter implements AttributeConverter<String,String>{

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return Encryptors.queryableText("${proposta.crypt.text}", "59486632").encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return Encryptors.queryableText("${proposta.crypt.text}", "59486632").decrypt(dbData);
	}

}
