package domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;

	//��Ź��ﳵ��ļ��� key����Ʒ��id cartitem:���ﳵ�� ʹ��map��������ɾ���������ﳵ��
	private Map<String,CartItem> map = new LinkedHashMap<>();
	
	//�ܽ��
	private Double total=0.0;
	
	/**
	 * ��ȡ���еĹ��ﳵ��
	 * @return
	 */
	public Collection<CartItem> getItems(){
		return map.values();
	}
	
/**
 * �����Ʒ�����ﳵ
 * @param item
 */
	public void add2Cart(CartItem item){
		//���жϹ��ﳵ�����޸���Ʒ
		//1.1 �Ȼ�ȡ��Ʒ��id
		String pid = item.getProduct().getPid();
		if(map.containsKey(pid)){
			//��
			//���ù�����������Ҫ��ȡ����Ʒ֮ǰ�Ĺ�������+���ڵĹ�������
			CartItem oItem = map.get(pid);
			oItem.setCount(oItem.getCount()+item.getCount());
		}
		else{
			//û��
			map.put(pid, item);
		}
		//2.������֮�� �޸Ľ��
		total+=item.getSubtotal();
	}
	
	/**
	 * �ӹ��ﳵɾ��
	 * @param pid
	 */
	public void removeFromCart(String pid){
		//1.�Ӽ�����ɾ��
		CartItem item =map.remove(pid);
		
		//2.�޸Ľ��
		total-=item.getSubtotal(); 
	}
	/**
	 *  ��չ��ﳵ
	 */
	public void clearCart(){
		//1.map�ÿ�
		map.clear();
		//2.������
		total=0.0;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
