package ppppp.mq.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {
    private String goodsId;
    private String goodsName;
    private double goodsPrice;
    private String goodsDesc;
}
