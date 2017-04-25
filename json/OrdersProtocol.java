//JSON �������� ��ü Ʋ�� �����ΰ�, �̰� ������ ���پ����� ����!
//
package json;

import java.sql.Timestamp;

public class OrdersProtocol {
	StringBuffer sb = new StringBuffer();
	
	public OrdersProtocol(int product_id, int orders_client_id) {
		sb.append("{");
		sb.append("\"requestType\":\"order\",");
		sb.append("\"product_id\":"+product_id+",");
		sb.append("\"orders_emp_id\":0,"); //�¶��� �ֹ��� ������ȣ�� 0���� ó��
		sb.append("\"orders_client_id\":"+orders_client_id+",");
		sb.append("\"orders_status\":\"ready\",");
		sb.append("\"orders_payment_type\":\"card\",");
		sb.append("\"orders_type\":\"online\"");
		sb.append("}");
	}
	public StringBuffer getProtocol(){
		return sb;
	}
}
