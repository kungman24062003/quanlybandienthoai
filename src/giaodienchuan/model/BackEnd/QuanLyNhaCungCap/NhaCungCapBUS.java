/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyNhaCungCap;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhaCungCapBUS {
    
    public ArrayList<NhaCungCap> dsncc = new ArrayList<>();
    NhaCungCapDAO qlnccDAO= new NhaCungCapDAO();
    public void show() {
        dsncc.forEach((ncc) -> {
            System.out.print(ncc.getMaNCC() + " ");
            System.out.print(ncc.getTenNCC() + " ");
            System.out.println(ncc.getDiaChi() + " ");
            System.out.println(ncc.getSDT() + " ");
            System.out.println(ncc.getFax());
        });
    }
    public NhaCungCapBUS()
    {
        dsncc=qlnccDAO.readDB();
    }
    public void readDB() {
        
        dsncc = qlnccDAO.readDB();
        
    }
    public ArrayList<NhaCungCap> search(String value, String type) {
        ArrayList<NhaCungCap> result = new ArrayList<>();

        dsncc.forEach((ncc) -> {
            if (type.equals("Tất cả")) {
                if (ncc.getMaNCC().toLowerCase().contains(value.toLowerCase())
                        || ncc.getTenNCC().toLowerCase().contains(value.toLowerCase())
                        || ncc.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ncc.getSDT()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ncc.getFax()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(ncc);
                }
            } else {
                switch (type) {
                    case "Mã nhà cung cấp":
                        if (ncc.getMaNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Tên nhà cung cấp":
                        if (ncc.getTenNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Địa chỉ":
                        if (ncc.getDiaChi().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "SĐT":
                        if (String.valueOf(ncc.getSDT()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Fax":
                        if (String.valueOf(ncc.getFax()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(NhaCungCap ncc) {
        
        Boolean ok = qlnccDAO.add(ncc);
        if (ok) {
            dsncc.add(ncc);
        }
        return ok;
    }

    public Boolean add(String ma, String ten, String diachi, String sdt, String fax) {
        NhaCungCap ncc = new NhaCungCap(ma, ten, diachi, sdt, fax);
        return add(ncc);
    }

    public Boolean delete(String mancc) {
        Boolean ok = qlnccDAO.delete(mancc);
       

        if (ok) {
            for (int i = (dsncc.size() - 1); i >= 0; i--) {
                if (dsncc.get(i).getMaNCC().equals(mancc)) {
                    dsncc.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String mancc, String tenncc, String diachi, String sdt, String fax) {
        
        Boolean ok = qlnccDAO.update(mancc, tenncc, diachi, sdt, fax);
       

        if (ok) {
            dsncc.forEach((ncc) -> {
                if (ncc.getMaNCC().equals(mancc)) {
                    ncc.setTenNCC(tenncc);
                    ncc.setDiaChi(diachi);
                    ncc.setSDT(sdt);
                    ncc.setFax(fax);
                }
            });
        }

        return ok;
    }
public ArrayList<NhaCungCap> getDsncc() {
        return dsncc;
    }
}