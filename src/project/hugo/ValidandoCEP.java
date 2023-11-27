package project.hugo;

import project.hugo.excepitions.ViaCepException;
import project.hugo.excepitions.ViaCepFormatException;

import java.util.Objects;

public class ValidandoCEP {
    public static void validaCep(String cep){
        if (Objects.isNull(cep) || cep.isEmpty() || cep.isBlank()) throw new ViaCepException("O cep informado não pode ser nulo ou vazio");
        if (cep.length() > 8) throw new ViaCepFormatException("CEP fora do formato, caso esteja com hifen, use o metodo de formatacao");
        if (cep.length() < 8) throw new ViaCepException("CEP faltando numeros");
    }
}
