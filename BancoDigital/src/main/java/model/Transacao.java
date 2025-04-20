package model;

import lombok.Getter;
import lombok.Setter;
import util.LocalDateTimeUtil;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transacao {
    private static int contador = 1;

    private int id;
    private LocalDateTime data;
    private TipoTransacao tipoTransacao;
    private double valor;
    private double taxa;

    public Transacao(LocalDateTime data, TipoTransacao tipoTransacao, double valor, double taxa) {
        this.id = contador++;
        this.data = data;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.taxa = taxa;
    }

    @Override
    public String toString() {
        if (taxa > 0) {
            return String.format("[%d] %s - R$ %.2f (TAXA: %.2f)\t\t\t%s",
                    id, tipoTransacao.getDescricao(), valor, taxa, LocalDateTimeUtil.formatarDataHora(data));
        }
        return String.format("[%d] %s - R$ %.2f\t\t\t%s",
                id, tipoTransacao.getDescricao(), valor, LocalDateTimeUtil.formatarDataHora(data));
    }
}
