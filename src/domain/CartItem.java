package domain;

import java.io.Serializable;

/**
 * ¹ºÎï³µÏî
 * @author wzw
 *
 */
public class CartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926360086228789701L;
	private Product product;
	private Integer count;
	private Double subtotal=0.0;
	public Product getProduct() {
		return product;
	}
	public CartItem(Product product, Integer count) {
		super();
		this.product = product;
		this.count = count;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return product.getShop_price()*count;
	}
	
	
}
