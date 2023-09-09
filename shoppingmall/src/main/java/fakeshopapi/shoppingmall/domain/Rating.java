package fakeshopapi.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
public class Rating {
    private Double rate;
    private Integer count;
}
