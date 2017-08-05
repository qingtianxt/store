package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.websocket.server.UpgradeUtil;

import domain.Category;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;
import utils.UUIDUtils;
import utils.UploadUtils;

/**
 * Servlet implementation class AddProductServlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//����map ����ǰ̨���ݵ�����
			HashMap<String,Object> map = new HashMap<>();
			
			//���������ļ���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			//���������ϴ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//����request
			List<FileItem> list = upload.parseRequest(request);
			
			//��������
			for (FileItem fi : list) {
				//�ж��Ƿ�����ͨ���ϴ����
				if(fi.isFormField()){
					//��ͨ�ϴ����
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else{
					//�ļ��ϴ����
					//��ȡ�ļ�����
					String name = fi.getName();
					
					//��ȡ�ļ�����ʵ����
					String realName = UploadUtils.getRealName(name);
					
					//��ȡ�ļ����������
					String uuidName = UploadUtils.getUUIDName(realName);
					//��ȡ�ļ��Ĵ��·��
					String path = this.getServletContext().getRealPath("/products/1");
					//��ȡ�ļ���
					InputStream is = fi.getInputStream();
					//����ͼƬ
					FileOutputStream os = new FileOutputStream(new File(path,uuidName));
					
					IOUtils.copy(is, os);
					os.close();
					is.close();
					//ɾ����ʱ�ļ�
					fi.delete();
					//��map�������ļ���·��
					map.put(fi.getFieldName(), "products/1/"+uuidName);
				}
				
				
			}
			
			//��װ����
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			//��Ʒid
			p.setPid(UUIDUtils.getId());
			//��Ʒʱ��
			p.setPdate(new Date());
			//��װcategory
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			//����service������
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.add(p);
			//ҳ���ض���
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "��Ʒ���ʧ��");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return ;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
