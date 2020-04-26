package cm.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import cm.dto.MedicineDto;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cm.mapper.MedicineMapper;
import cm.pojo.Agency;
import cm.pojo.EasyUIResult;
import cm.pojo.Medicine;

@Service
@Transactional
public class MedicineService {
	@Autowired
	private MedicineMapper medicineMapper;

	public Medicine queryMedicineByMno(String mno) {
		// TODO Auto-generated method stub
		Medicine medicine = medicineMapper.queryMedicineByMno(mno);
		return medicine;

	}

	public EasyUIResult queryAllMedicine(Integer page, Integer rows, MedicineDto medicineDto) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<Medicine> medicines = medicineMapper.queryAllMedicine(medicineDto);
		PageInfo<Medicine> pageInfo = new PageInfo<Medicine>(medicines);
		return new EasyUIResult(pageInfo.getTotal(), medicines);
	}

	public String saveMedicine(Medicine medicine) {
		// TODO Auto-generated method stub
		if (queryMedicineByMno(medicine.getMno()) != null) {
			return "药品编号已经存在，请重新输入编号";
		}
		medicineMapper.saveMedicine(medicine);
		return "保存成功";
	}

	public String deleteMedicineByMno(String mno) {
		// TODO Auto-generated method stub
		try {
			medicineMapper.deleteMedicineByMno(mno);
		} catch (Exception e) {
			// TODO: handle exception
			return "删除失败，可能是有客户购买此药，" + "无法删除此药品";
		}
		return "删除成功";
	}

	public String modifyMedicine(Medicine medicine) {
		// TODO Auto-generated method stub
		Medicine queryMedicineByMno = queryMedicineByMno(medicine.getMno());
		if (queryMedicineByMno == null) {
				return "这个药品编号数据库不存在，无法更改！可能是页面修改药品编号导致的！";
		}
		try {
            medicine.setMid(queryMedicineByMno.getMid());
			medicineMapper.modifyMedicine(medicine);
		} catch (Exception e) {
			// TODO: handle exception
			return "修改失败，可能是有客户购买此药，" + "无法修改编号";
		}
		return "修改成功";
	}

	public List<Medicine> getAllMedicine() {
		// TODO Auto-generated method stub
		MedicineDto dto =new MedicineDto();
		List<Medicine> queryAllMedicine = medicineMapper.queryAllMedicine(dto);
		return queryAllMedicine;
	}

	public String deleteMedicineByRows(String[] array) {
		// TODO Auto-generated method stub
		try {
			for (String mno : array) {
				medicineMapper.deleteMedicineByMno(mno);
			}
		} catch (Exception e) {
			// TODO: handle exception
			// 抛出异常让spring捕获，进行事务回滚
			throw new RuntimeException();

		}
		return "删除成功";
	}

	public String queryMultiMedicine(Medicine medicine, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			List<Medicine> medicines = medicineMapper.queryMultiMedicine(medicine);
			session.setAttribute("medicines", medicines);
			System.out.println(medicines);
			System.out.println("@@@@@");
			return "";
		} catch (Exception e) {
			// TODO: handle exception
			return "操作异常，请刷新后操作";
		}

		// System.out.println(medicine);
		// List<Medicine> medicine1 = (List<Medicine>)
		// medicineMapper.queryMultiMedicine(medicine);
		// System.out.println(medicine1);
	}

	public EasyUIResult getMultiMedicine(Integer page, Integer rows, HttpSession session) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<Medicine> medicines = (List<Medicine>) session.getAttribute("medicines");
		System.out.println(medicines);
		PageInfo<Medicine> pageInfo = new PageInfo<Medicine>(medicines);
		return new EasyUIResult(pageInfo.getTotal(), medicines);
	}
}